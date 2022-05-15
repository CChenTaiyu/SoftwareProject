from flask import Flask
from flask import render_template
from flask import request
from flask_login import current_user
from flask_login import LoginManager, login_user
from flask_sqlalchemy import SQLAlchemy
from flask_login import UserMixin

app = Flask(__name__)
app.config["SQLALCHEMY_DATABASE_URI"] = "sqlite:///" + "user.db"
app.config["SQLALCHEMY_TRACK_MODIFICATIONS"] = False
app.config["SECRET_KEY"] = "123456"

db = SQLAlchemy(app)


class User(db.Model, UserMixin):
    __tablename__ = "user"
    id = db.Column(db.Integer, primary_key=True)  # 主键
    username = db.Column(db.String(20), nullable=False, unique=True)  # 名字是否可以为空
    password = db.Column(db.String(20), nullable=False)
    phone = db.Column(db.Integer, default=False)
    gender = db.Column(db.String(1))
    height = db.Column(db.Float(6), default=False)
    weight = db.Column(db.Float(6), default=False)


class Course(db.Model, UserMixin):
    __tablename__ = "Course"
    id = db.Column(db.Integer, primary_key=True)  # 主键
    course_name = db.Column(db.String(20), nullable=False, unique=True)
    date = db.Column(db.Integer, nullable=False)
    capacity = db.Column(db.Integer, default=False)  # 客程容量
    applicants = db.Column(db.Integer, default=False)  # 已报名人数
    count = db.Column(db.Integer, default=False)
    category = db.Column(db.String(20), default=False)
    duration = db.Column(db.String(20), default=False)  # 时长
    description = db.Column(db.String(200), default=False)


class Feedback(db.Model, UserMixin):
    __tablename__ = "Feedback"
    id = db.Column(db.Integer, primary_key=True, default=False)  # 主键
    content = db.Column(db.String(100), nullable=False, unique=True)


class User_course(db.Model, UserMixin):
    __tablename__ = "User_course"
    id = db.Column(db.Integer, primary_key=True)  # 主键
    username = db.Column(db.String(20), nullable=False)
    courseid = db.Column(db.Integer, nullable=False)
    coursename = db.Column(db.String(20), nullable=False)


login_manager = LoginManager(app)
login_manager.login_view = 'login'
login_manager.init_app(app)


@login_manager.user_loader
def load_user(user_id):
    user = User.query.get(user_id)
    return user


def existed(u, p):
    users = User.query.filter_by(username=u).first()
    if users:
        if users.password == p:
            return 1  #
        else:
            return 2  # 密码错误
    else:
        return 3  # 不存在的用户名


@app.route('/')
def go_to_login():
    return render_template("/Login1.html")


# 登录
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == "POST":
        username = request.form.get("username", type=str, default=None)
        password = request.form.get("password", type=str, default=None)
        if username == "admin" and password == "admin123":  # 以管理员身份登录
            user = User.query.filter_by(username=username).first()
            login_user(user, remember=True)
            data = Course.query.filter().all()
            return render_template("/index_admin.html", user=current_user, course=data)
        elif existed(username, password) == 1:
            message = "Login successfully!"
            user = User.query.filter_by(username=username).first()
            login_user(user, remember=True)
            data = Course.query.filter().all()
            data2 = User_course.query.filter(User_course.username == current_user.username).all()
            return render_template("/index.html", user=current_user, message=message, course=data, mine=data2)
        elif existed(username, password) == 2:
            message = "Wrong password!"
            return render_template("/Login1.html", message=message)
        elif existed(username, password) == 3:
            message = "Username does not exist!"
            return render_template("/Login1.html", message=message)
    return render_template("/Login1.html", user=current_user)


@app.route('/index', methods=['GET', 'POST'])
def index():
    data = User_course.query.filter(User_course.username == current_user.username).all()
    return render_template("/index.html", user=current_user, mine=data)


@app.route('/go_to_register', methods=['GET', 'POST'])
def go_to_register():
    return render_template("/register.html", user=current_user)


# 注册
@app.route('/register', methods=['GET', 'POST'])
def register():
    if request.method == "POST":
        username = request.form.get("username", type=str, default=None)
        password = request.form.get("password", type=str, default=None)
        password1 = request.form.get("password1", type=str, default=None)
        if len(username) == 0 or len(password) == 0:
            message = "Username or password cannot be empty"
            return render_template("/Login1.html", message=message)
        if password == password1:
            if User.query.filter_by(username=username).first():
                message = "Username already exists!"
                return render_template("/Login1.html", message=message)
            else:
                message = "Registration success!"
                new_user = User(username=username, password=password, height=0.1)
                db.session.add(new_user)
                db.session.commit()
                return render_template("/Login1.html", message=message)
        else:
            message = "Please enter the same password!"
            return render_template("/Login1.html", message=message)
    return render_template("Login1.html")


# 登出
@app.route('/logout', methods=['GET', 'POST'])
def logout():
    return render_template("/Login1.html")


# 修改密码
@app.route('/go_to_change_password', methods=['GET', 'POST'])
def go_to_change_password():
    return render_template("/change_password.html", user=current_user)


@app.route('/change_password', methods=['GET', 'POST'])
def change_password():
    if request.method == "POST":
        password = request.form.get("password", type=str, default=None)
        new_password1 = request.form.get("password1", type=str, default=None)
        new_password2 = request.form.get("password2", type=str, default=None)
        user = current_user
        if new_password1 == new_password2:
            if existed(user.username, password) == 1:
                message = "Reset successfully!"
                user.password = new_password1
                db.session.commit()
                return render_template("/Login1.html", message=message)
            else:
                message = "Wrong password"
                return render_template("/change_password.html", message=message, user=current_user)
        else:
            message = "Passwords don't match!"
            return render_template("/change_password.html", message=message, user=current_user)
    return render_template("/login.html")


@app.route('/goto_usercenter', methods=['GET', 'POST'])
def goto_usercenter():
    return render_template("/usercenter.html", user=current_user)


@app.route('/change_ge', methods=['GET', 'POST'])
def change_ge():
    if request.method == "POST":
        gender = request.form.get("gender", type=str)
        user = current_user
        if gender == "Male":
            user.gender = gender
            db.session.commit()
            return render_template("/usercenter.html", user=current_user)
        elif gender == "Female":
            user.gender = gender
            db.session.commit()
            return render_template("/usercenter.html", user=current_user)
    return render_template("/usercenter.html", user=current_user)


@app.route('/change_he', methods=['GET', 'POST'])
def change_he():
    if request.method == "POST":
        height = request.form.get("height", type=str)
        user = current_user
        if len(height) != 0:
            user.height = height
            db.session.commit()
            return render_template("/usercenter.html", user=current_user)
        else:
            return render_template("/usercenter.html", user=current_user)
    return render_template("/usercenter.html", user=current_user)


@app.route('/change_we', methods=['GET', 'POST'])
def change_we():
    if request.method == "POST":
        weight = request.form.get("weight", type=str)
        user = current_user
        if len(weight) != 0:
            user.weight = weight
            db.session.commit()
            return render_template("/usercenter.html", user=current_user)
        else:
            return render_template("/usercenter.html", user=current_user)
    return render_template("/usercenter.html", user=current_user)


@app.route('/change_ph', methods=['GET', 'POST'])
def change_ph():
    if request.method == "POST":
        phone = request.form.get("phone", type=str)
        user = current_user
        if len(phone) != 0:
            user.phone = phone
            db.session.commit()
            return render_template("/usercenter.html", user=current_user)
        else:
            return render_template("/usercenter.html", user=current_user)
    return render_template("/usercenter.html", user=current_user)


@app.route('/send_feedback', methods=['GET', 'POST'])
def send_feedback():
    if request.method == "POST":
        user_feedback = request.form.get("feedback", type=str, default=None)
        feedback = Feedback(content=user_feedback)
        db.session.add(feedback)
        db.session.commit()
    return render_template("/usercenter.html", user=current_user)


@app.route('/goto_feedback', methods=['GET', 'POST'])
def goto_feedback():
    data = Feedback.query.filter().all()
    return render_template("/feedback.html", user=current_user, feedback=data)


@app.route('/delete_feedback', methods=['GET', 'POST'])
def delete_feedback():
    if request.method == "POST":
        id = request.form.get("id", type=str)
        Feedback.query.filter(Feedback.id == id).delete()
        db.session.commit()
        data = Feedback.query.filter().all()
    return render_template("/feedback.html", user=current_user, feedback=data)


@app.route('/course_mana', methods=['GET', 'POST'])
def goto_course_mana():
    data = Course.query.filter().all()
    return render_template("/management.html", course=data, user=current_user)


@app.route('/add_course', methods=['GET', 'POST'])
def add_course():
    if request.method == "POST":
        course_name = request.form.get("course_name", type=str, default=None)
        date = request.form.get("date", type=str, default=None)
        capacity = request.form.get("capacity", type=str, default=None)
        count = request.form.get("count", type=str, default=None)
        category = request.form.get("category", type=str, default=None)
        duration = request.form.get("duration", type=str, default=None)
        description = request.form.get("description", type=str, default=None)
        course = Course(course_name=course_name, date=date, capacity=capacity, applicants=0, count=count,
                        category=category, duration=duration, description=description)
        db.session.add(course)
        db.session.commit()
        data = Course.query.filter().all()
    return render_template("/management.html", user=current_user, course=data)


@app.route('/delete_course', methods=['GET', 'POST'])
def delete_course():
    if request.method == "POST":
        id = request.form.get("id", type=str)
        data = Course.query.filter().all()
        if Course.query.filter(Course.id == id).first():
            course = Course.query.filter(Course.id == id).first()
            User_course.query.filter(User_course.courseid == course.id).delete()
            Course.query.filter(Course.id == id).delete()
            db.session.commit()
            return render_template("/management.html", user=current_user, Course=data)
        else:
            return render_template("/management.html", user=current_user, Course=data)


@app.route("/join_course", methods=["POST"])
def join_course():
    if request.method == "POST":
        data_all = Course.query.filter().all()
        inputid = request.form.get("id", default=None)
        user = current_user
        if Course.query.filter(Course.id == inputid).first():
            course = Course.query.filter(Course.id == inputid).first()
            if course.capacity - course.applicants > 0:  # 参与课程
                if User_course.query.filter(
                        User_course.id == inputid and User_course.username == current_user.username).first():
                    data = User_course.query.filter(User_course.username == current_user.username).all()
                    return render_template("/index.html", user=current_user, mine=data, course=data_all)
                else:
                    course.applicants += 1  # 已报名人数+1
                    user_course = User_course(username=user.username, courseid=course.id,
                                              coursename=course.course_name)  # 用户加入课程
                    db.session.add(user_course)
                    db.session.commit()
                    data = User_course.query.filter(User_course.username == current_user.username).all()
                    return render_template("/index.html", user=current_user, mine=data, course=data_all)
            elif course.capacity - course.applicants == 0:
                data = User_course.query.filter(User_course.username == current_user.username).all()
                return render_template("/index.html", title="success", user=current_user, mine=data, course=data_all)
        else:
            data = User_course.query.filter(User_course.username == current_user.username).all()
            return render_template("/index.html", title="success", user=current_user, mine=data, course=data_all)
    data = User_course.query.filter(User_course.username == current_user.username).all()
    return render_template("/index.html", title="Change√", user=current_user, mine=data, course=data_all)


@app.route("/quit_course", methods=["POST"])
def quit_course():
    if request.method == "POST":
        data_all = Course.query.filter().all()
        inputid = request.form.get("id", default=None)
        course = Course.query.filter(Course.id == inputid).first()
        if User_course.query.filter(
                User_course.id == inputid and User_course.username == current_user.username).first():
            course.applicants -= 1  # 已报名人数-1
            User_course.query.filter(
                User_course.id == inputid and User_course.username == current_user.username).delete()
            db.session.commit()
            data = User_course.query.filter(User_course.username == current_user.username).all()
            return render_template("/index.html", user=current_user, mine=data, course=data_all)
        else:
            data = User_course.query.filter(User_course.username == current_user.username).all()
            return render_template("/index.html", user=current_user, mine=data, course=data_all)
        data = User_course.query.filter(User_course.username == current_user.username).all()
        return render_template("/index.html", title="Change√", user=current_user, mine=data, course=data_all)


if __name__ == '__main__':
    db.create_all()
    app.run(debug=True)
