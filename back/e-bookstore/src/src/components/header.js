import React from "react";
import title from "../img/E-BookStore.png";
import avatar from "../img/tx.jpg"
import {useSelector} from "react-redux";
export default function Header() {
    const account=JSON.parse((useSelector((state)=>state.account)).accountInfo);
    //console.log(account);
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
                    <button type="button"
                            className="group leading-6 font-medium flex items-center space-x-3 sm:space-x-4 hover:text-gray-600 transition-colors duration-200">
                        <svg width="24" height="24" fill="none"
                             className="text-gray-400 group-hover:text-gray-500 transition-colors duration-200">
                            <path d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" stroke="currentColor" stroke-width="2"
                                  stroke-linecap="round" stroke-linejoin="round"></path>
                        </svg>
                        <span>搜索<span className="hidden sm:inline"> </span></span><span
                        className="hidden sm:block text-gray-400 text-sm leading-5 py-0.5 px-1.5 border-solid border border-gray-300 rounded-md"><span
                        className="sr-only">Press </span><kbd className="font-sans"><abbr title="Control"
                                                                                          className="no-underline">Ctrl </abbr></kbd><span
                        className="sr-only"> and </span><kbd className="font-sans">K</kbd><span
                        className="sr-only"> to search</span></span>
                    </button>
                    <div className="flex items-center space-x-6">
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
                        <a href={"/PersonalPage"} >
                            <img
                                src={avatar}
                                className={"h-10 w-10 min-w-10 rounded-3xl border-solid border border-gray-200 duration-200"}
                            />
                        </a>
                    </div>
                </div>
            </div>
        </>
    )
}