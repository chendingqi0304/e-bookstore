import Header from "../components/header";
import React, {useEffect} from "react";
import {useState} from "react";
import {InputNumber, Modal, Space, Table, Typography} from "antd";
import {AddBook, AllBookList, DeleteBook, EditBook, RecoverBook, SearchByTitle} from "../utils/BookAPI";

const {Paragraph} = Typography;
const AllBook = () => {


    const [loading, setLoading] = useState(true);

    const [bookList, setBookList] = React.useState([]);


    const columns = [
        {title: "购买链接",
        dataIndex: "bookId",
        key: "bookId",
        render: (text) =>
            <a href={`/book/${text}`}><div class="min-w-16 text-blue-400 underline">购买链接</div></a>

        },
        {
            title: "封面",
            dataIndex: "picture",
            key: "picture",
            render: (item) => <div class="flex items-center"><img src={`data:${item.type};base64, ${item}`}/></div>
        },
        {
            title: "书名",
            dataIndex: 'title',
            key: 'title',
            render: (text) => <div className="min-w-20">{text}</div>
        },
        {
            title: "ISBN",
            dataIndex: 'isbn',
            key: 'isbn',
            render: (text) => <div class="min-w-20">{text}</div>
        },
        {
            title: '原价',
            dataIndex: 'originprice',
            key: 'originprice',
            render: (price) => (
                <div>￥{(price / 100).toFixed(2)}</div>
            )
        },
        {
            title: '现价',
            dataIndex: 'price',
            key: 'price',
            render: (price) => (
                <div>￥{(price / 100).toFixed(2)}</div>
            )
        },
        {
            title: '作者',
            dataIndex: 'author',
            key: 'author',
            render: (text) => (
                <div class={"min-w-20"}><Paragraph
                    ellipsis={
                        {
                            rows: 2,
                            expandable: false,
                            symbol: 'more',
                        }
                    }
                >
                    {text}
                </Paragraph></div>)
        }, {
            title: '简介',
            dataIndex: 'introduction',
            key: 'introduction',
            render: (text) => (<Paragraph
                ellipsis={
                    {
                        rows: 2,
                        expandable: false,
                        symbol: 'more',
                    }
                }
            >
                {text}
            </Paragraph>)
        }, {
            title: '余量',
            dataIndex: 'rest',
            key: 'rest',
            render: (text) => <div>{text}</div>
        }]


    useEffect(() => {
        const getBookList = async () => {
            const result = await AllBookList();
            if (result.code === 1) {
                const list=[];
                result.data.map((book) => {
                    if (!book.deleted){
                        list.push(book);
                    }
                })
                setBookList(list);
                setLoading(false);
            } else {
                alert(result.msg)
            }
        }
        getBookList().then();
    }, [])




    const handleSearch = async () => {
        setLoading(true);
        const title = document.getElementById("search").value
        if (title === null) {
            alert("请输入书名")
            return
        }
        const formData = new FormData()
        formData.append("title", title)
        const result = await SearchByTitle(formData)
        if (result.code === 1) {
            const list=[];
            result.data.map((book) => {
                if (!book.deleted){
                    list.push(book);
                }
            })
            setBookList(list);
        } else {
            alert(result.msg)
        }
        setLoading(false)
    }
    const showList = (e) => {
        if (loading) return (<></>)
        else return (<>
            <Table className="px-10" columns={columns} dataSource={bookList}/>
        </>)
    }


    return <>
        <html lang="en">
        <head>
            <meta charset="UTF-8"/>
            <title>E-BookStore</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <link href="../dist/output.css" rel="stylesheet"/>
        </head>
        <body>
        <Header class=""></Header>
        <div class="mx-24 mt-8">
            <div class="text-3xl py-5">全部书籍</div>

            <div class="flex items-center">
                <input type="text" className="my-3 mx-3 border border-solid rounded-md" id="search"
                       placeholder={"搜索书名"}></input>
                <button onClick={handleSearch}
                        class="focus:outline-none text-sm w-auto py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-6 justify-center items-center text-center gap-5">搜索
                </button>
            </div>
            {showList(loading)}

        </div>

        </body>
        </html>
    </>

}
export default AllBook;