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
import AdminRoute from "./components/AdminRoute";
import AllBook from "./page/AllBook";
import OrderManagement from "./page/OrderManagement";
import BookAuthor from "./page/BookAuthor";
import BookTag from "./page/BookTag";

function App() {

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<LoginPage/>}/>
                <Route path="/forgetPwd" element={<ForgetPwd/>}/>
                <Route path="/register" element={<Register/>}/>

                <Route path="/Main" element={<PrivateRoute><MainPage/></PrivateRoute>}/>
                <Route path="/PersonalPage" element={<PrivateRoute><PersonalPage/></PrivateRoute>}/>
                <Route path="/Mycart/:name" element={<PrivateRoute><Mycart/></PrivateRoute>}/>
                <Route path="/book/:name" element={<PrivateRoute><BuyBook/></PrivateRoute>}/>
                <Route path="/AllBook" element={<PrivateRoute><AllBook/></PrivateRoute>}></Route>
                <Route path="/Myorder/:name" element={<PrivateRoute><Myorder/></PrivateRoute>}/>
                <Route path="/BookAuthor" element={<PrivateRoute><BookAuthor/></PrivateRoute>}></Route>
                <Route path="/BookTag" element={<PrivateRoute><BookTag/></PrivateRoute>}></Route>

                <Route path="/bookManagement" element={<AdminRoute><BookManagement/></AdminRoute>}/>
                <Route path="/userManagement" element={<AdminRoute><UserManagement/></AdminRoute>}/>
                <Route path="/List" element={<AdminRoute><List/></AdminRoute>}/>
                <Route path="/orderManagement" element={<AdminRoute><OrderManagement/></AdminRoute>}/>

                <Route path="*" element={<Navigate to="/login" replace />} />
                    {/* 其他路由规则 */}
            </Routes>
        </Router>
    );
}

export default App;
