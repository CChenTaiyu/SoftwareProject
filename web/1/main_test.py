from flask import Flask, jsonify
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


# 登录
@app.route('/login', methods=['GET', 'POST'])
def login():
    if request.method == "POST":
        username = request.form.get("username", type=str, default=None)
        password = request.form.get("password", type=str, default=None)
        if username == "admin" and password == "admin123":  # 以管理员身份登录
            user = User.query.filter_by(username=username).first()
            login_user(user, remember=True)
            message = "Login successfully!"
            return jsonify(code=99999, message=message)
        elif existed(username, password) == 1:
            message = "Login successfully!"
            user = User.query.filter_by(username=username).first()
            login_user(user, remember=True)
            return jsonify(code=99999, message=message)
        elif existed(username, password) == 2:
            message = "Wrong password!"
            return jsonify(code=99999, message=message)
        elif existed(username, password) == 3:
            message = "Username does not exist!"
            return jsonify(code=99999, message=message)


# 注册
@app.route('/register', methods=['GET', 'POST'])
def register():
    if request.method == "POST":
        username = request.form.get("username", type=str, default=None)
        password = request.form.get("password", type=str, default=None)
        password1 = request.form.get("password1", type=str, default=None)
        if len(username) == 0 or len(password) == 0:
            message = "Username or password cannot be empty"
            return jsonify(code=99999, message=message)
        if password == password1:
            if User.query.filter_by(username=username).first():
                message = "Username already exists!"
                return jsonify(code=99999, message=message)
            else:
                message = "Registration success!"
                new_user = User(username=username, password=password, height=0.1)
                db.session.add(new_user)
                db.session.commit()
                return jsonify(code=99999, message=message)
        else:
            message = "Please enter the same password!"
            return jsonify(code=99999, message=message)


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
                return jsonify(code=99999, message=message)
            else:
                message = "Wrong password"
                return jsonify(code=99999, message=message)
        else:
            message = "Passwords don't match!"
            return jsonify(code=99999, message=message)


@app.route('/change_he', methods=['GET', 'POST'])
def change_he():
    if request.method == "POST":
        height = request.form.get("height", type=str)
        user = current_user
        if len(height) != 0:
            user.height = height
            db.session.commit()
            message = "input is empty"
            return jsonify(code=99999, message=message)
        else:
            message = "Right input"
            return jsonify(code=99999, message=message)


@app.route("/join_course", methods=["POST"])
def join_course():
    if request.method == "POST":
        inputid = request.form.get("id", default=None)
        user = current_user
        if Course.query.filter(Course.id == inputid).first():
            course = Course.query.filter(Course.id == inputid).first()
            if course.capacity - course.applicants > 0:  # 参与课程
                if User_course.query.filter(
                        User_course.id == inputid and User_course.username == current_user.username).first():
                    message = "Case1"
                    return jsonify(code=99999, message=message)
                else:
                    course.applicants += 1  # 已报名人数+1
                    user_course = User_course(username=user.username, courseid=course.id,
                                              coursename=course.course_name)  # 用户加入课程
                    db.session.add(user_course)
                    db.session.commit()
                    message = "Case2"
                    return jsonify(code=99999, message=message)
            elif course.capacity - course.applicants == 0:
                message = "Case3"
                return jsonify(code=99999, message=message)
        else:
            message = "Case4"
            return jsonify(code=99999, message=message)

    message = "Case5"
    return jsonify(code=99999, message=message)


if __name__ == '__main__':
    db.create_all()
    app.run(debug=True)
