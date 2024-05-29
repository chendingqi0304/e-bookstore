import React from "react";
import title from "../img/E-BookStore.png"
import MC from "../img/MC.png"
import Header from "../components/header"
import {useSelector} from "react-redux";
import {useState} from "react";
import {useEffect} from "react";

const MainPage = () => {
    const account = JSON.parse((useSelector((state) => state.account)).accountInfo);
    const [bookList, setBookList] = useState([]);

    useEffect(() => {
        async function fetchBookList() {
            try {
                const response = await fetch("http://localhost:8080/booklist", {
                    method: "POST",
                    credentials: "include",
                })
                if (response.ok) {
                    const data = await response.json();
                    if (data.code === 1) {
                        console.log(data.data);
                        setBookList(data.data);
                    } else {

                    }
                } else {
                    alert("获取书籍列表失败");
                }
            } catch (error) {
                throw new Error("获取书籍列表失败", error)
            }
        }

        fetchBookList().then(r => {
        });
    }, []);

    console.log(bookList);

    return (
        <>
            <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <title>E-BookStore</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <link href="../dist/output.css" rel="stylesheet"/>
            </head>

            <body>
            <Header></Header>

            <div class="lib">
                <div class="slide">

                    <ul class="swiper">
                        <a href="/book/1" class="pic bg-white/50">
                            <div class=" csapp">
                                <div class="h-96"></div>
                                <div class=" bg-black/50 h-full p-11"><p
                                    class=" text-2xl text-white">《csapp》——计算机“圣经”</p>
                                </div>
                            </div>
                        </a>
                        <a href="/book/2" class="pic bg-white/50">
                            <div class="DS bg-white/85">
                                <div class="h-96"></div>
                                <div class=" bg-black/50 h-full p-11"><p class=" text-2xl text-white">
                                    《数据结构》——高效算法的必要基础</p></div>
                            </div>
                        </a>
                        <a href="/Main" class="pic bg-white/50">
                            <div class="MC bg-white/85">
                                <div class="h-96"></div>
                                <div class=" bg-black/50 h-full p-11"><p
                                    class=" text-2xl text-white">锋利V——战士利器</p></div>
                            </div>
                        </a>
                        <a href="/book/4" class="pic bg-white/50">
                            <div class="hz bg-white/85">
                                <div class="h-96"></div>
                                <div class=" bg-black/50 h-full p-11"><p class=" text-2xl text-white">
                                    《活着》——演绎人生苦难经历的戏剧</p></div>
                            </div>
                        </a>
                        <a href="/book/1" class="pic bg-white/50">
                            <div class="csapp bg-white/85">
                                <div class="h-96"></div>
                                <div class=" bg-black/50 h-full p-11"><p
                                    class=" text-2xl text-white">《csapp》——计算机“圣经”</p>
                                </div>
                            </div>
                        </a>
                    </ul>

                    <div class="circle flex justify-center items-center gap-x-2.5">
                        <div class="active h-2.5 w-2.5 float-left bg-black rounded-xl"></div>
                        <div
                            class="h-2.5 w-2.5 float-left border-solid border-white border-1 bg-white/0 rounded-xl"></div>
                        <div
                            class="h-2.5 w-2.5 float-left border-solid border-white border-1 bg-white/0 rounded-xl"></div>
                        <div
                            class="h-2.5 w-2.5 float-left border-solid border-white border-1 bg-white/0 rounded-xl"></div>
                        <div
                            class="h-2.5 w-2.5 float-left border-solid border-white border-1 bg-white/0 rounded-xl"></div>
                        <div
                            class="h-2.5 w-2.5 float-left border-solid border-white/0 border-1 bg-white/0 rounded-xl"></div>
                    </div>
                </div>
            </div>


            <div class=" p-28">
                <div class="text-center h-24 flex justify-center items-center gap-9 text-3xl">
                    <img src={MC} class="h-16"/>为你推荐<img src={MC} class="h-16"/>
                </div>

                <ul class="grid grid-cols-2 sm:grid-cols-3 xl:grid-cols-3 gap-4 sm:gap-12 xl:gap-x-28 font-semibold text-gray-900 text-center">
                    {bookList.map(item=><>
                    <li class="flex items-center justify-center">
                        <a
                            class="rounded-xl ring-1 ring-black ring-opacity-5 shadow-sm w-full pt-8 pb-6 px-6"
                            href={`/book/${item.bookId}`}>
                            <div class="flex items-center justify-center"><img className="h-96" src={`data:${item.type};base64, ${item.picture}`}/></div>
                            <div class="flex items-center justify-center">
                                <div className="text-red-700 text-xl">￥{(item.price/100).toFixed(2)}</div>
                            </div>

                            {item.title}</a>
                    </li>
                    </>)}
                </ul>
            </div>
            <div class="text-center text-3xl">特别鸣谢</div>
            <ul class="grid grid-cols-2 sm:grid-cols-3 xl:grid-cols-3 gap-4 sm:gap-12 xl:gap-x-28 font-semibold text-gray-900 text-center p-28">
                <li class="flex">
                    <div class="relative rounded-xl ring-1 ring-black ring-opacity-5 shadow-sm w-full pt-8 pb-8">
                        <div
                            class="text-center h-12 flex justify-center items-center text-2xl">·&nbsp;&nbsp;蔡先躬李岳翔
                        </div>
                        首席图片处理官
                    </div>
                </li>
                <li class="flex">
                    <div class="relative rounded-xl ring-1 ring-black ring-opacity-5 shadow-sm w-full pt-8 pb-8">
                        <div class="text-center h-12 flex justify-center items-center text-2xl">·&nbsp;&nbsp;刘涵旭
                        </div>
                        残忍甲方嘴替
                    </div>
                </li>
                <li class="flex">
                    <div class="relative rounded-xl ring-1 ring-black ring-opacity-5 shadow-sm w-full pt-8 pb-8">
                        <div
                            class="text-center h-12 flex justify-center items-center text-2xl">·&nbsp;&nbsp;知天易逆天男[3]
                        </div>
                        提不出任何建议的辅助
                    </div>
                </li>
            </ul>

            </body>
            </html>
        </>
    );
};
export default MainPage