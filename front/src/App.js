//项目的根组件
//App -> index.js  -> public/index.html(root)
//import "./index.css";

import {BrowserRouter as Router, Navigate, Route, Routes} from "react-router-dom";
import "./dist/output.css"
import ForgetPwd from "./page/forgetPwd";
import MainPage from "./page/MainPage"
import Login from "./page/login";
import PersonalPage from "./page/PersonalHomepage"
import BuyBook from "./page/buyBook"
import Mycart from "./page/Mycart"
import Myorder from "./page/Myorder"
import DataManagement from "./page/dataManagement";

function App() {

    return (
        <Router>
            <Routes>
                <Route path="/login" element={<Login/>}/>
                <Route path="/Main" element={<MainPage/>}/>
                <Route path="/forgetPwd" element={<ForgetPwd/>}/>
                <Route path="/PersonalPage" element={<PersonalPage/>}/>
                <Route path="/Mycart/:name" element={<Mycart/>}/>
                <Route path="/book/:name" element={<BuyBook/>}/>
                <Route path="/dataManagement" element={<DataManagement/>}/>
                <Route path="/Myorder/:name" element={<Myorder/>}/>
                <Route path="*" element={<Navigate to="/login" replace />} />
                    {/* 其他路由规则 */}
            </Routes>
        </Router>
    );
}

export default App;
