import title from "../img/E-BookStore.png";
import React, {useEffect, useState} from "react";
import Header from "../components/header"
import {useParams} from 'react-router-dom';
import {useSelector} from "react-redux";
import {Button, Flex, InputNumber, Modal, Slider, Switch, Typography} from 'antd';
import {GetBook} from "../utils/BookAPI";
import {AddCartByBookId} from "../utils/CartAPI";
import {BuyByBookId} from "../utils/OrderAPI";
import {connectWebSocket, sendMessage} from "../utils/WebSocketAPI";

const BuyBook = () => {
    const backendLink = useSelector((state) => state.backendLink.backendLink);
    const account = JSON.parse((useSelector((state) => state.account)).accountInfo);
    const [book, setBook] = useState("");
    const [bookId, setBookId] = useState("");
    const [rows, setRows] = useState(10);
    const [ModalShow, setModalShow] = useState(false);
    const [ModalShow1, setModalShow1] = useState(false);
    const [modalStr, setModalStr] = useState("");
    const [mode, setMode] = useState("");
    const [booknumber, setBooknumber] = useState(1);

    useEffect(() => {
        async function fetchBook(link) {
            const formdata = new FormData();
            formdata.append("bookId", parseInt(link));
            const result = await GetBook(formdata)
            if (result.code === 1) {
                result.data.originprice = (result.data.originprice).toFixed(2);
                result.data.price = (result.data.price).toFixed(2);
                setBook(result.data);
            } else {
                alert(result.msg)
            }
        }

        const link = window.location.href;
        const index = link.lastIndexOf("/");
        setBookId(link.substring(index + 1));
        fetchBook(link.substring(index + 1)).then(() => {
        });
    }, [])


    function price(book) {

        if (book.price === book.originprice) {
            return (
                <>
                    <div className="text-gray-700">定价：<span
                        className="text-red-700 text-2xl">￥{(book.price / 100).toFixed(2)}</span></div>
                </>
            )
        } else {
            return (
                <>
                    <div className="text-gray-700">定价：<span
                        className="text-red-700 text-xl line-through">￥{(book.originprice / 100).toFixed(2)}</span>
                        <span className="text-red-700 text-2xl">￥{(book.price / 100).toFixed(2)}</span></div>
                </>
            )
        }
    }

    const {name} = useParams();

    //var book = bookInfo.find((bookInformation => bookInformation.name === name));
    async function handleSubmit() {
        if (mode === 1) {
            const formdata = new FormData();
            formdata.append("bookId", parseInt(bookId));
            formdata.append("number", booknumber);
            //console.log(bookId)
            const result = await BuyByBookId(formdata)
            await connectWebSocket(orderResult)

            if (result.code === 1) {
                setModalStr("提交订单成功,订单正在处理");
                setModalShow(false);
                setModalShow1(true);
            } else {
                alert(result.msg)
            }
        }//提交订单
        else if (mode === 0) {
            const formdata = new FormData();
            formdata.append("bookId", parseInt(bookId));
            formdata.append("number", booknumber);
            const result = await AddCartByBookId(formdata)
            if (result.code === 1) {
                setModalStr("已添加到购物车");
                setModalShow(false);
                setModalShow1(true);
            } else {
                alert(result.msg)
            }

        }//购物车
    }

    const orderResult = (result) => {
        result = JSON.parse(result);

        if (result.code === 1 && result.data === "Success") {
            setModalStr("订单处理完成");
            setModalShow1(true)
        }

    }

    function closeModal() {
        setModalShow(false);
    }

    function closeModal1() {
        setModalShow1(false)
    }

    function cartClick() {
        setMode(0);
        setModalShow(true);
    }

    function buyClick() {
        setMode(1);
        setModalShow(true);
    }

    const numberChange = (value) => {
        console.log(value);
        setBooknumber(value);
    }

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
            <Modal
                open={ModalShow}
                onOk={handleSubmit}
                onCancel={closeModal}
                afterClose={closeModal}
                okText={"提交"}
                cancelText={"取消"}
                okType={"default"}

            >
                <div class="text-2xl mb-2">{mode === 1 ? "提交订单" : "添加到购物车"}</div>
                {mode === 1 ? <div>输入地址:<input class="border-solid border-1 border-gray-300 rounded-md ml-2 mb-2"/>
                </div> : <></>}
                <div class="flex text-center items-center ">选择数量:
                    <div class="ml-2"><InputNumber defaultValue={1} min={1} max={20} onChange={numberChange}
                                                   value={booknumber}></InputNumber></div>
                </div>

            </Modal>
            <Modal
                open={ModalShow1}
                closable={false}
                footer={<Button
                    onClick={closeModal1}
                >
                    确认
                </Button>}
            >
                <div>{modalStr}</div>

            </Modal>
            <div class="w-full h-auto flex items-center justify-center">
                <div class="grid grid-cols-2 gap-20 pt-10 w-auto">
                    <div className="flex justify-end items-center text-center">
                        <div
                            className="rounded-xl ring-2 ring-black ring-opacity-5 h-auto pt-4 pb-6 px-6">
                            <img className="h-96" src={`data:${book.type};base64, ${book.picture}`} alt={"书籍图片"}/>
                        </div>
                    </div>
                    <div className="h-auto w-2/3 p-5 leading-9 rounded-xl ring-2 ring-black ring-opacity-5">
                        <div className="text-3xl text-red-700">{book.title}</div>
                        <div className="text-gray-700">作者：{book.author}</div>
                        <div className="text-gray-700">ISBN：{book.isbn}</div>
                        {/*<div class="text-gray-700">类别：{book.type}</div>*/}
                        {price(book)}
                        <div className="text-gray-700">状态：{book.state}
                            <span className="text-gray-400 pl-2">库存 {book.rest} 本</span>
                        </div>
                        <Flex gap={16} vertical>
                            <Typography.Paragraph
                                ellipsis={{
                                    rows,
                                    expandable: false,
                                }}
                                copyable={false}
                            >
                                简介：{book.introduction}
                            </Typography.Paragraph>
                        </Flex>
                    </div>
                </div>
            </div>
            <div className={"justify-center items-center text-center gap-20 flex pt-10"}>
                <button
                    className="focus:outline-none text-sm w-auto py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-16 justify-center items-center text-center gap-5"
                    type="button" id="cartbtn" onClick={cartClick}>
                    <svg className="icon text-white fill-current" viewBox="0 0 1024 1024"
                         width="20" height="20">
                        <path
                            d="M346.112 806.912q19.456 0 36.864 7.168t30.208 19.968 20.48 30.208 7.68 36.864-7.68 36.864-20.48 30.208-30.208 20.48-36.864 7.68q-20.48 0-37.888-7.68t-30.208-20.48-20.48-30.208-7.68-36.864 7.68-36.864 20.48-30.208 30.208-19.968 37.888-7.168zM772.096 808.96q19.456 0 37.376 7.168t30.72 19.968 20.48 30.208 7.68 36.864-7.68 36.864-20.48 30.208-30.72 20.48-37.376 7.68-36.864-7.68-30.208-20.48-20.48-30.208-7.68-36.864 7.68-36.864 20.48-30.208 30.208-19.968 36.864-7.168zM944.128 227.328q28.672 0 44.544 7.68t22.528 18.944 6.144 24.064-3.584 22.016-13.312 37.888-22.016 62.976-23.552 68.096-18.944 53.248q-13.312 40.96-33.28 56.832t-49.664 15.872l-35.84 0-65.536 0-86.016 0-96.256 0-253.952 0 14.336 92.16 517.12 0q49.152 0 49.152 41.984 0 20.48-9.728 35.328t-38.4 14.848l-49.152 0-94.208 0-118.784 0-119.808 0-99.328 0-55.296 0q-20.48 0-34.304-9.216t-23.04-24.064-14.848-32.256-8.704-32.768q-1.024-6.144-5.632-29.696t-11.264-58.88-14.848-78.848-16.384-87.552q-19.456-103.424-44.032-230.4l-76.8 0q-15.36 0-25.6-7.68t-16.896-18.432-9.216-23.04-2.56-22.528q0-20.48 13.824-33.792t37.376-13.312l21.504 0 21.504 0 25.6 0 34.816 0q20.48 0 32.768 6.144t19.456 15.36 10.24 19.456 5.12 17.408q2.048 8.192 4.096 23.04t4.096 30.208q3.072 18.432 6.144 38.912l700.416 0zM867.328 194.56l-374.784 0 135.168-135.168q23.552-23.552 51.712-24.064t51.712 23.04z"
                        ></path>
                    </svg>
                    <span>加入购物车</span>
                </button>
                <button
                    className="focus:outline-none text-sm w-auto py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-16 justify-center items-center text-center gap-5"
                    type="button" id="buybtn" onClick={buyClick}>
                    <svg className="icon text-white fill-current" viewBox="0 0 1024 1024"
                         width="30" height="30">
                        <path
                            d="M938.726888 481.287227c-0.722653-15.175723-13.007763-26.015526-28.183486-25.292873s-26.015526 13.730416-25.292873 28.183487c0 6.503881 0.722653 13.007763 0.722654 20.234297 0 211.737474-171.991531 384.451658-384.451659 384.451659s-384.451658-171.991531-384.451658-384.451659 171.991531-384.451658 384.451658-384.451658c56.366972 0 110.565984 11.562456 161.151729 35.410021l17.343684 8.671842c13.007763 7.226535 29.628793 2.16796 36.132675-11.562456 7.226535-13.007763 2.16796-29.628793-11.562456-36.132675-6.503881-3.613267-13.007763-6.503881-19.511644-9.394495-57.812279-26.738179-119.237826-40.468596-183.553988-40.468596-241.366267 0-437.928017 196.56175-437.928017 437.928017s196.56175 437.928017 437.928017 437.928017S939.449541 745.778405 939.449541 504.412138c0-7.949188-0.722653-15.175723-0.722653-23.124911z"
                        ></path>
                        <path
                            d="M893.199718 377.947777m-28.90614 0a28.90614 28.90614 0 1 0 57.812279 0 28.90614 28.90614 0 1 0-57.812279 0Z"
                        ></path>
                        <path
                            d="M849.840508 283.280169m-28.90614 0a28.90614 28.90614 0 1 0 57.81228 0 28.90614 28.90614 0 1 0-57.81228 0Z"
                        ></path>
                        <path
                            d="M779.743119 201.620325m-28.906139 0a28.90614 28.90614 0 1 0 57.812279 0 28.90614 28.90614 0 1 0-57.812279 0Z"
                        ></path>
                        <path
                            d="M622.927311 381.561044l36.855328-83.105151c5.781228-13.730416 0-29.628793-13.730416-35.410021-13.730416-5.781228-29.628793 0-35.410021 13.730416l-43.35921 98.280875-2.16796 6.503881h-99.726182c-0.722653-2.16796-0.722653-4.335921-2.167961-6.503881l-43.359209-98.280875c-5.781228-13.730416-21.679605-19.511644-35.410022-13.730416s-19.511644 21.679605-13.730416 35.410021l36.855328 83.105151h-96.112914c-15.175723 0-26.738179 12.285109-26.73818 26.73818s12.285109 26.738179 26.73818 26.738179h168.378264v137.304164h-168.378264c-15.175723 0-26.738179 12.285109-26.73818 26.738179s12.285109 26.738179 26.73818 26.738179h168.378264v134.41355c0 15.175723 12.285109 26.738179 26.738179 26.738179 15.175723 0 26.738179-12.285109 26.738179-26.738179v-134.41355h169.100917c15.175723 0 26.738179-12.285109 26.73818-26.738179s-12.285109-26.738179-26.73818-26.738179h-169.82357v-137.304164h169.100917c15.175723 0 26.738179-12.285109 26.738179-26.738179s-12.285109-26.738179-26.738179-26.73818h-78.769231z"
                        ></path>
                    </svg>
                    <span>立即购买</span>
                </button>
            </div>
            </body>
            </html>
        </>
    )
}
export default BuyBook