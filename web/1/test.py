import unittest
from main_test import app
import json


class TestMain(unittest.TestCase):
    def setUp(self):
        self.app = app
        app.config['TESTING'] = True
        self.client = app.test_client()

    def test_wrong_password_login(self):
        response = self.client.post("/login", data={"username": "lxy", "password": "123"})

        resp_json = response.data

        resp_dict = json.loads(resp_json)

        self.assertIn("code", resp_dict)

        code = resp_dict.get("code")
        self.assertEqual(code, 99999)

        msg = resp_dict.get("message")
        self.assertEqual(msg, "Wrong password")

    def test_wrong_username_login(self):
        response = self.client.post("/login", data={"username": "12345", "password": "123"})

        resp_json = response.data

        resp_dict = json.loads(resp_json)

        self.assertIn("code", resp_dict)

        code = resp_dict.get("code")
        self.assertEqual(code, 99999)

        msg = resp_dict.get("message")
        self.assertEqual(msg, "Username does not exist")

    def test_existed_username_register(self):
        response = self.client.post("/register", data={"username": "123", "password": "123", "password": "123"})

        resp_json = response.data

        resp_dict = json.loads(resp_json)

        self.assertIn("code", resp_dict)

        code = resp_dict.get("code")
        self.assertEqual(code, 99999)

        msg = resp_dict.get("message")
        self.assertEqual(msg, "User already exists")

    def test_different_password_register(self):
        response = self.client.post("/register", data={"username": "1234", "password": "123", "password1": "1234"})

        resp_json = response.data

        resp_dict = json.loads(resp_json)

        self.assertIn("code", resp_dict)

        code = resp_dict.get("code")
        self.assertEqual(code, 99999)

        msg = resp_dict.get("message")
        self.assertEqual(msg, "Passwords don't match")

    def test_wrong_password_reset(self):
        response = self.client.post("/change_password", data={"username": "123", "password": "1234",
                                                              "password1": "1234", "password1": "1234"})

        resp_json = response.data

        resp_dict = json.loads(resp_json)

        self.assertIn("code", resp_dict)

        code = resp_dict.get("code")
        self.assertEqual(code, 99999)

        msg = resp_dict.get("message")
        self.assertEqual(msg, "Passwords don't match")

    def test_different_password_reset(self):
        response = self.client.post("/change_password", data={"username": "123", "password": "1233",
                                                              "password1": "1234", "password1": "12345"})

        resp_json = response.data

        resp_dict = json.loads(resp_json)

        self.assertIn("code", resp_dict)

        code = resp_dict.get("code")
        self.assertEqual(code, 99999)

        msg = resp_dict.get("message")
        self.assertEqual(msg, "Passwords don't match")


if __name__ == '__main__':
    unittest.main()
