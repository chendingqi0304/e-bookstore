import {useRef} from "react";
import register from "../img/register.jpg";
import React, {useState, useEffect} from "react";
import User_Logo from "../img/user_logo.png";
import Email_Logo from "../img/email_logo.png";
import Phone_Logo from "../img/phone_logo.png";
import Password_Logo from "../img/password_logo.png";
import Captcha_Logo from "../img/captcha_logo.png";
import {useDispatch, useSelector} from "react-redux";
import {FindUserByEmail, NewUser} from "../utils/UserAPI";
import {GetIdentifyCode, Identify} from "../utils/IdentifyAPI";



const RegisterPage = () => {
    const inputRef1 = useRef(null);
    const inputRef2 = useRef(null);
    const inputRef3 = useRef(null);
    const inputRef4 = useRef(null);
    const inputRef5 = useRef(null);

    const [Username, ChangeUserName] = useState("");
    const [Email, ChangeEmail] = useState("");
    const [PassWord, ChangePassword] = useState("");
    const [PassWordRE, ChangePasswordRe] = useState("");
    const [Captcha, ChangeCaptcha] = useState("");


    const handleUsername = (e) => {
        ChangeUserName(e.target.value);
        inputRef1.current.focus();
    };
    // const handleMail = (e) => {
    //   ChangeMail(e.target.value);
    //   inputRef2.current.focus();
    // };
    const handleEmail = (e) => {
        ChangeEmail(e.target.value);
        inputRef2.current.focus();
    };
    const handlePassword = (e) => {
        ChangePassword(e.target.value);
        inputRef3.current.focus();
    };
    const handlePasswordRe = (e) => {
        ChangePasswordRe(e.target.value);
        inputRef4.current.focus();
    };

    const handleCaptcha = (e) => {
        ChangeCaptcha(e.target.value);
        inputRef5.current.focus();
    };

    const handleClick = async (e) => {
        if (Username === "" || Email === "" || PassWord === "" || PassWordRE === "") {
            const element1 = document.getElementById("username");
            const element2 = document.getElementById("email");
            const element3 = document.getElementById("password1");
            const element4 = document.getElementById("password2");
            const element5 = document.getElementById("captcha");
            if (Username === "") element1.classList.add("border-red-500");
            else element1.classList.remove("border-red-500");
            if (Email === "") element2.classList.add("border-red-500");
            else element2.classList.remove("border-red-500");
            if (PassWord === "") element3.classList.add("border-red-500");
            else element3.classList.remove("border-red-500");
            if (PassWordRE === "") element4.classList.add("border-red-500");
            else element4.classList.remove("border-red-500");
            if (Captcha === "") element5.classList.add("border-red-500");
            else element5.classList.remove("border-red-500");
            alert("请填写完整全部信息");
        } else if (PassWord === PassWordRE) {
            const data = await Identify(Email, Captcha);
            if (data.code === 1) {
                const formdata=new FormData;
                formdata.append("username", Username);
                formdata.append("email", Email);
                formdata.append("password", PassWord);
                const result = await NewUser(formdata);
                if (result.code === 1) {
                    alert("注册成功，您的账号为：" + result.data);
                    window.location.href = "/";
                } else {
                    alert("该邮箱或用户名已被注册");
                }
            } else {
                alert(data.msg);
            }
        } else {
            alert("两次输入密码不一致");
        }
    };

    const [count, setCount] = useState(60); // 倒计时时间
    const [disabled, setDisabled] = useState(false); // 按钮是否禁用

    useEffect(() => {
        let intervalId;
        if (disabled) {
            intervalId = setInterval(() => {
                setCount((prevCount) => {
                    if (prevCount === 0) {
                        clearInterval(intervalId);
                        setDisabled(false); // 倒计时结束后启用按钮
                        return 60; // 重置倒计时时间
                    }
                    return prevCount - 1;
                });
            }, 1000);
        }
        return () => clearInterval(intervalId); // 组件卸载时清除计时器
    }, [disabled]);

    const handlecaptchaClick = async () => {
        const email = document.getElementById("email").value;
        if(email==null){
            alert("请输入邮箱")
            return
        }
        const data = await FindUserByEmail(email);
        if (data.code === 1) {
            alert("该邮箱已被注册");
            return;
        }
        const data2 = await GetIdentifyCode(email);
        if (data2.code === 1) {
            alert("邮件已成功发送，请及时查看邮箱及垃圾邮件");
            setDisabled(true);// 点击按钮后禁用按钮
        } else {
            alert(data2.msg);
        }
    };
    return (
        <>
            <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <meta
                    name="viewport"
                    content="width=device-width, initial-scale=1.0"
                />
                <link href="../dist/output.css" rel="stylesheet"/>
                <title>register</title>
            </head>

            <body class="h-screen"
                  style={{
                      backgroundImage: `url(${register})`,
                      backgroundRepeat: "no-repeat",
                      backgroundSize: "cover",
                      backgroundPosition: "center",
                  }}
            >
            <div class="h-24"></div>
            <div class="container mx-auto">
                <div class="flex justify-center">
                    <form
                        class="bg-white shadow-md rounded px-8 pt-6 pb-8 mb-4 bg-gray-100 bg-opacity-50 p-4 border-2 rounded-lg">
                        <div className="h-6"></div>
                        <div className="mb-4">
                            <div className="text-3xl text-center">
                                新用户注册
                            </div>
                        </div>
                        <div className="mb-4">
                            {/* <?xml version="1.0" standalone="no"?><!DOCTYPE svg PUBLIC "-//W3C//DTD SVG 1.1//EN" "http://www.w3.org/Graphics/SVG/1.1/DTD/svg11.dtd"><svg t="1709642685049" class="icon inline-block" viewBox="0 0 1024 1024" version="1.1" xmlns="http://www.w3.org/2000/svg" p-id="5933" xmlns:xlink="http://www.w3.org/1999/xlink" width="16" height="16"><path d="M513.536 628.6336c101.6832 0 280.8832 38.7072 310.8864 193.7408 77.5168-77.5168 124.928-185.9584 124.928-305.0496 0-241.152-194.6624-435.8144-435.8144-435.8144S77.7216 277.0944 77.7216 517.2224c0 119.0912 47.4112 227.6352 124.928 306.0736 30.0032-154.9312 209.2032-194.6624 310.8864-194.6624z m0-439.7056c112.3328 0 204.3904 91.0336 204.3904 204.3904 0 112.3328-91.0336 204.3904-204.3904 204.3904a203.776 203.776 0 0 1-204.3904-204.3904c0.1024-112.4352 92.0576-204.3904 204.3904-204.3904z m0 0" fill="#1296DB" p-id="5934"></path><path d="M515.4816 1006.8992c-65.8432 0-129.8432-12.9024-189.952-38.4-58.1632-24.576-110.2848-59.8016-155.136-104.5504A485.56032 485.56032 0 0 1 65.8432 708.8128c-25.6-60.2112-38.5024-124.1088-38.5024-189.952 0-65.8432 12.9024-129.8432 38.4-189.952 24.576-58.1632 59.8016-110.2848 104.5504-155.136 44.8512-44.8512 96.9728-79.9744 155.136-104.5504C385.6384 43.6224 449.536 30.72 515.4816 30.72c65.8432 0 129.8432 12.9024 189.952 38.4 58.1632 24.576 110.2848 59.8016 155.136 104.5504 44.8512 44.8512 79.9744 96.9728 104.5504 155.136C990.6176 389.0176 1003.52 452.9152 1003.52 518.8608c0 65.8432-12.9024 129.8432-38.4 189.952-24.576 58.1632-59.8016 110.2848-104.5504 155.136a485.56032 485.56032 0 0 1-155.136 104.5504 484.39296 484.39296 0 0 1-189.952 38.4z m0-936.8576c-60.6208 0-119.3984 11.8784-174.6944 35.2256a447.76448 447.76448 0 0 0-142.6432 96.1536A444.42624 444.42624 0 0 0 101.9904 344.064a446.70976 446.70976 0 0 0-35.2256 174.6944c0 60.6208 11.8784 119.3984 35.2256 174.6944 22.6304 53.4528 54.9888 101.4784 96.1536 142.6432 41.1648 41.2672 89.1904 73.6256 142.6432 96.1536 55.296 23.3472 114.0736 35.2256 174.6944 35.2256 60.6208 0 119.3984-11.8784 174.6944-35.2256 53.4528-22.6304 101.4784-54.9888 142.6432-96.1536 41.2672-41.1648 73.6256-89.1904 96.1536-142.6432 23.3472-55.296 35.2256-114.0736 35.2256-174.6944 0-60.6208-11.8784-119.3984-35.2256-174.6944a447.76448 447.76448 0 0 0-96.1536-142.6432 444.42624 444.42624 0 0 0-142.6432-96.1536 447.44704 447.44704 0 0 0-174.6944-35.2256z m0 0" fill="#1296DB" p-id="5935"></path></svg> */}
                            <img
                                src={User_Logo}
                                className="inline-block h-4 w-4"
                                alt="user"
                            ></img>
                            <label
                                className="inline-block mb-2"
                                htmlFor="username"
                            >
                                用户名:
                            </label>
                            <input
                                ref={inputRef1}
                                onChange={handleUsername}
                                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                id="username"
                                type="text"
                                placeholder=""
                            />
                        </div>
                        <div className="mb-4">
                            <div className="flex items-center align-middle">
                                <svg className="h-6 fill-current text-blue-600 " viewBox="0 0 1024 1024">
                                    <path
                                        d="M893.842 217.557H130.159c-16.261 0-29.443 13.182-29.443 29.443v530c0 16.261 13.182 29.443 29.443 29.443h763.683c16.261 0 29.443-13.183 29.443-29.443V247c0-16.261-13.182-29.443-29.443-29.443z m-85.584 58.886L512 507.651 215.742 276.443h592.516zM159.602 747.557v-440.23l334.283 260.885A29.4 29.4 0 0 0 512 574.443a29.4 29.4 0 0 0 18.115-6.231l334.283-260.884v440.229H159.602z"></path>
                                </svg>
                                <label className="inline-block mb-2">
                                    邮箱地址
                                </label>
                            </div>

                            <div>
                                <input
                                    ref={inputRef2}
                                    id="email"
                                    onChange={handleEmail}
                                    className="shadow appearance-none border rounded w-2/3 py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                    placeholder=""
                                />
                                <input
                                    id="btnSendCode"
                                    className="text-blue-400 font-bold"
                                    type="button"
                                    value={disabled ? `    ${count}秒再获取` : '    获取验证码'}
                                    onClick={handlecaptchaClick}
                                    disabled={disabled}
                                />
                            </div>

                        </div>
                        <div className="mb-4">
                            <img src={Captcha_Logo}
                                 className="inline-block h-4 w-4"
                                 alt="captcha"
                            ></img>
                            <label className="inline-block mb-2">
                                验证码
                            </label>
                            <input
                                id="captcha"
                                ref={inputRef5}
                                onChange={handleCaptcha}
                                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 leading-tight focus:outline-none focus:shadow-outline"
                                placeholder=""
                            />
                        </div>
                        <div className="mb-1">
                            <img
                                src={Password_Logo}
                                className="inline-block h-4 w-4"
                                alt="password"
                            ></img>
                            <label
                                className="inline-block mb-2"
                                htmlFor="password"
                            >
                                密码:
                            </label>
                            <input
                                ref={inputRef3}
                                onChange={handlePassword}
                                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
                                id="password1"
                                type="password"
                                placeholder=""
                            />
                        </div>
                        <div className="mb-4">
                            <img
                                src={Password_Logo}
                                className="inline-block h-4 w-4"
                                alt="password"
                            ></img>
                            <label
                                className="inline-block mb-2"
                                htmlFor="password"
                            >
                                重新输入密码:
                            </label>
                            <input
                                ref={inputRef4}
                                onChange={handlePasswordRe}
                                className="shadow appearance-none border rounded w-full py-2 px-3 text-gray-700 mb-3 leading-tight focus:outline-none focus:shadow-outline"
                                id="password2"
                                type="password"
                                placeholder=""
                            />
                        </div>
                        <div id="MyRegister" className="flex items-center justify-center">
                            <button
                                className="focus:outline-none text-sm w-16 py-3 rounded-md font-semibold text-white bg-blue-500 ring-4"
                                type="button"
                                id="registerButton"
                                onClick={handleClick}
                            >
                                注册
                            </button>
                        </div>
                    </form>
                </div>
            </div>
            </body>
            </html>
        </>
    );
};

export default RegisterPage;
