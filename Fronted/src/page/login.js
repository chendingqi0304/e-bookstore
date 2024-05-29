import React, {useState, useRef} from 'react';
import background from '../img/login-background.png'
import {useDispatch,useSelector} from "react-redux";
import {changeAccount} from "../store/modules/accountStore";
import {Login} from "../utils/LoginAPI";

const LoginPage = () => {

    const inputRef1 = useRef(null);
    const inputRef2 = useRef(null);

    const [Account, ChangeAccount] = useState('');
    const [Password, ChangePassword] = useState('');
    const [payload, ChangePayLoad] = useState('');
    const dispatch=useDispatch();

    const handleAccount = (e) => {
        ChangeAccount(e.target.value);
        inputRef1.current.focus();
        ChangePayLoad({
            "account": e.target.value,
            "password": Password
        })
    }
    const handlePassword = (e) => {
        ChangePassword(e.target.value);
        inputRef2.current.focus();
        ChangePayLoad({
            "account": Account,
            "password": e.target.value
        })
    }
    const onKeyDown = (e) => {
        if (e.key === 'Enter')
            handleClick(e)
    }

    const handleClick =async (e) => {
        const element1 = document.getElementById("account");
        const element2 = document.getElementById("password");
        if (element1.value === "" || element2.value === "") {
            ChangeAccount(element1.value)
            ChangePassword(element2.value);
            if (element1.value === "") element1.classList.add("border-red-500");
            else element1.classList.remove("border-red-500");
            if (element2.value === "") element2.classList.add("border-red-500");
            else element2.classList.remove("border-red-500");
            alert("请填写完整登录信息")
            return;
        }
        const result=await Login(payload)
        if(result.code===1){
            dispatch(changeAccount(result.data));
            window.location.href = "/Main";
        }else{
            alert(result.msg);
        }
    }

    return (
        <>
            <html>
            <head>
                <meta charset="UTF-8"/>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <link href="../dist/output.css" rel="stylesheet"/>

            </head>
            <body style={{
                backgroundImage: `url('${background}')`,
                backgroundSize: `cover`,
                backgroundRepeat: `no-repeat`,
                backgroundPosition: `center`,
            }}>
            {/* <!-- flex弹性布局 justify-center水平方向上使内部元素居中对齐 items-center垂直方向上 h-screen高度整个屏幕--> */}
            <div class="flex justify-center items-center h-screen">

                {/* <!--box-border盒子边界模式，宽高不包括边框  bg-gray-200背景灰色 shdow-md设置中等阴影 rounded-lg较大的圆角 relative相对位置容器--> */}
                <div class="h-96 w-96 bg-gray-100 bg-opacity-50 p-4 border-2 shadow-md rounded-lg relative">
                    {/* <!-- absolute绝对定位 top-1/10距离父容器1/10 left-0左侧距离父容器0 --> */}
                    <div class="absolute top-8 left-0 w-full ">
                        <p class="text-center text-black-500 text-3xl mt-4 ">登录</p>
                    </div>
                    <div class="absolute top-24 left-0 h-36 w-full ">
                        <div class="box-border relative">
                            <div class="absolute top-1/10 left-0 w-full relative">
                                <p class="absolute left-12 ext-black-500 text-1xl">请输入账号:</p>
                            </div>
                            <br/>
                            <div class="h-2"></div>
                            <div class="flex justify-center">
                                <input type="text" ref={inputRef1} onChange={handleAccount} id="account"
                                       class="p-2 w-4/5 left-2 center rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
                                       placeholder="Account"/>
                            </div>
                        </div>
                        <div className="absolute top-24 h-1/3 left-0 w-full -mt-1">
                            <div className="box-border relative">
                                <div className="absolute top-1/10 left-0 w-full relative">
                                    <p class="absolute left-12 ext-black-500 text-1xl">请输入密码:</p>
                                </div>
                                <br/>
                                <div className="h-2"></div>
                                <div className="flex justify-center">
                                    <input id="password" ref={inputRef2} onKeyDown={onKeyDown} onChange={handlePassword}
                                           type="password"
                                           class="p-2 w-4/5 left-2 center rounded-md border border-gray-300 focus:outline-none focus:border-blue-500"
                                           placeholder="Password"/>
                                </div>
                            </div>
                            <div className="h-4"></div>
                            <div className="text-center h-12 flex justify-center items-center gap-24 p-4">
                                <a href="/Register">
                                    <button
                                        className="focus:outline-none text-sm w-16 py-3 rounded-md font-semibold text-white bg-blue-500 ring-4"
                                        type="submit">注册
                                    </button>
                                </a>
                                <button
                                    onClick={handleClick}
                                    className="focus:outline-none text-sm w-16 py-3 rounded-md font-semibold text-white bg-blue-500 ring-4"
                                    type="submit">登录
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            </body>
            </html>
        </>
    )
}
export default LoginPage;
