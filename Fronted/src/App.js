//项目的根组件
//App -> index.js  -> public/index.html(root)
//import "./index.css";

import {BrowserRouter as Router, Navigate, Route, Routes} from "react-router-dom";
import "./dist/output.css"
import ForgetPwd from "./page/forgetPwd";
import MainPage from "./page/MainPage"
import LoginPage from "./page/login";
import PersonalPage from "./page/PersonalHomepage"
import BuyBook from "./page/buyBook"
import Mycart from "./page/Mycart"
import Myorder from "./page/Myorder"
import BookManagement from "./page/BookManagement";
import PrivateRoute from "./components/PrivateRoute";
import Register from "./page/Register";
import UserManagement from "./page/UserManagement";
import List from "./page/List";

function App() {

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/Main" element={<MainPage/>}/>
                <Route path="/forgetPwd" element={<ForgetPwd/>}/>
                <Route path="/PersonalPage" element={<PersonalPage/>}/>
                <Route path="/Mycart/:name" element={<Mycart/>}/>
                <Route path="/book/:name" element={<BuyBook/>}/>
                <Route path="/Myorder/:name" element={<Myorder/>}/>
                <Route path="/register" element={<Register/>}/>
                <Route path="/bookManagement" element={<PrivateRoute><BookManagement/></PrivateRoute>}/>
                <Route path="/userManagement" element={<PrivateRoute><UserManagement/></PrivateRoute>}/>
                <Route path="/List" element={<PrivateRoute><List/></PrivateRoute>}/>
                <Route path="*" element={<Navigate to="/login" replace />} />
                    {/* 其他路由规则 */}
            </Routes>
        </Router>
    );
}

export default App;
