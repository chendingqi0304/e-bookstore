import React from "react";
import title from "../img/E-BookStore.png";
import avatar from "../img/tx.jpg"
import {useSelector} from "react-redux";

export default function Header() {
    const account = JSON.parse((useSelector((state) => state.account)).accountInfo);
    console.log(account)
    return (
        <>
            <div
                className="sticky top-0 z-40 lg:z-50 h-16 w-full max-w-8xl mx-auto bg-white flex-none flex border-solid border-b border-gray-200 shadow-md shadow-gray-200">
                <div
                    className="pl-8 flex items-center border-solid border-b border-gray-200">
                    <a className="overflow-hidden w-60 text-2xl" href="/Main">
                        <img src={title} alt="E-BookStore"/>
                    </a>
                </div>
                <div
                    className="flex-auto border-solid border-b border-gray-200 h-18 flex items-center justify-between px-4 sm:px-6 lg:mx-6 lg:px-0 xl:mx-8">
                    <div></div>
                    <div className="flex items-center space-x-6">
                        {account.type === 1 ? <>
                                <a href={`/bookManagement`}
                                   className="text-gray-400 hover:text-gray-500 transition-colors duration-200 underline h-auto w-auto">
                                    <div>书籍管理</div>
                                </a>
                                <a href={`/userManagement`}
                                   className="text-gray-400 hover:text-gray-500 transition-colors duration-200 underline h-auto w-auto">
                                    <div>用户管理</div>
                                </a>
                                <a href={`/list`}
                                   className="text-gray-400 hover:text-gray-500 transition-colors duration-200 underline h-auto w-auto">
                                    <div>表单统计</div>
                                </a>
                            </>

                            : <></>}
                        <a href={`/Mycart/${account.userId}`}
                           className="text-gray-400 hover:text-gray-500 transition-colors duration-200 underline h-auto w-auto">
                            <div>购物车</div>
                        </a>
                        <a href="/PersonalPage"
                           className="text-gray-400 hover:text-gray-500 transition-colors duration-200 underline h-auto w-auto">
                            <div>个人主页</div>
                        </a>
                        <a href={`/Myorder/${account.userId}`}
                           className="text-gray-400 hover:text-gray-500 transition-colors duration-200 underline  h-auto w-auto">
                            <div>我的订单</div>
                        </a>
                    </div>
                </div>
            </div>
        </>
    )
}