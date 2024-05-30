import Header from "../components/header";
import React, {useEffect} from "react";
import {useState} from "react";
import {InputNumber, Modal, Space, Table, Typography} from "antd";
import {AddBook, AllBookList, DeleteBook, EditBook, RecoverBook, SearchByTitle} from "../utils/BookAPI";

const {Paragraph} = Typography;
const BookManagement = () => {
    const [loading, setLoading] = useState(true);
    const [picture, setPicture] = React.useState("");
    const [bookList, setBookList] = React.useState([]);
    const [ModalShow, setModalShow] = useState(false);
    const [BookId, setBookId] = React.useState("");
    const [BookTitle, setBookTitle] = React.useState("");
    const [BookPrice, setBookPrice] = React.useState("");
    const [BookOriginprice, setBookOriginprice] = React.useState("");
    const [BookIntroduction, setBookIntroduction] = React.useState("");
    const [BookAuthor, setBookAuthor] = React.useState("");
    const [BookRest, setBookRest] = React.useState("");
    const [BookISBN, setBookISBN] = React.useState("");
    const handlePicture = (e) => {
        setPicture(e.target.files[0]);
    }

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
            render: (item) => <div className="flex items-center"><img src={`data:${item.type};base64, ${item}`}/></div>
        },
        {
            title: "书名",
            dataIndex: 'title',
            key: 'title',
            render: (text) => <div class="min-w-20">{text}</div>
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
        }, {
            title: '是否被删除',
            dataIndex: 'deleted',
            key: 'deleted',
            render: (text) => (<div class="min-w-20">{text ? "是" : "否"}</div>)
        },
        {
            title: "操作",
            key: 'action',
            render: (_, record, rowIndex) => (
                <Space size="middle">
                    {bookList[rowIndex].deleted ? <button id={"Recover" + rowIndex} className="text-blue-400"
                                                          onClick={handleRecover}>Recover</button>
                        : <button id={"Delete" + rowIndex} className="text-blue-400"
                                  onClick={handleDelete}>Delete</button>
                    }
                    <button id={"Edit" + rowIndex} class="text-blue-400" onClick={handleEdit}>Edit</button>
                </Space>
            )
        }]
    const handleRecover = async (e) => {
        const index = e.target.id.substring(7);
        console.log(index);
        const formData = new FormData();
        formData.append("bookId", bookList[index].bookId);
        const result = await RecoverBook(formData)
        if (result.code === 1) {
            window.location.reload()
        } else {
            alert(result.msg)
        }
    }
    const handleDelete = async (e) => {
        const index = e.target.id.substring(6);
        console.log(index);
        const formData = new FormData();
        formData.append("bookId", bookList[index].bookId);
        const result = await DeleteBook(formData)
        if (result.code === 1) {
            window.location.reload()
        } else {
            alert(result.msg);
        }
    }
    const handleEdit = (e) => {
        const editIndex = e.target.id.substring(4);
        console.log(editIndex);
        setBookId(bookList[editIndex].bookId);
        setBookTitle(bookList[editIndex].title);
        setBookPrice(bookList[editIndex].price / 100);
        setBookOriginprice(bookList[editIndex].originprice / 100);
        setBookIntroduction(bookList[editIndex].introduction);
        setBookAuthor(bookList[editIndex].author);
        setPicture(bookList[editIndex].picture);
        setBookRest(bookList[editIndex].rest)
        setBookISBN(bookList[editIndex].isbn)
        console.log(BookPrice)
        setModalShow(true);
    }

    useEffect(() => {
        const getBookList = async () => {
            const result = await AllBookList();
            if (result.code === 1) {
                setBookList(result.data);
                setLoading(false);
            } else {
                alert(result.msg)
            }
        }
        getBookList().then();
    }, [])

    const BookSubmit = async () => {
        var bookname = document.getElementById("bookname").value;
        var introduction = document.getElementById("introduction").value;
        var author = document.getElementById("author").value;
        var isbn = document.getElementById("isbn").value;
        console.log(picture);
        var formdata = new FormData();
        formdata.append("bookname", bookname);
        formdata.append("originprice", BookOriginprice * 100);
        formdata.append("price", BookPrice * 100);
        formdata.append("introduction", introduction);
        formdata.append("author", author);
        formdata.append("picture", picture);
        formdata.append("rest", BookRest);
        formdata.append("isbn", isbn)
        await AddBook(formdata);
    }

    const BookEdit = async () => {
        const formdata = new FormData();
        formdata.append("bookId", BookId)
        formdata.append("bookname", BookTitle);
        formdata.append("originprice", BookOriginprice * 100);
        formdata.append("price", BookPrice * 100);
        formdata.append("introduction", BookIntroduction)
        formdata.append("author", BookAuthor);
        formdata.append("rest", BookRest);
        formdata.append("picture", picture);
        formdata.append("isbn", BookISBN)
        const result = await EditBook(formdata)
        console.log(result)
        if (result.code === 1) {
            window.location.reload();
        } else {
            alert(result.msg)
        }
    }

    function closeModal() {
        setModalShow(false);
    }

    const onChangeTitle = async (e) => {
        setBookTitle(e.target.value)
    }
    const onChangeISBN = async (e) => {
        setBookISBN(e.target.value)
    }
    const onChangePrice = async (e) => {
        setBookPrice(e)
    }

    const onChangeOriginprice = async (e) => {
        setBookOriginprice(e)
    }

    const onChangeIntroduction = async (e) => {
        setBookIntroduction(e.target.value)
    }
    const onChangeAuthor = async (e) => {
        setBookAuthor(e.target.value)
    }
    const onChangeRest = async (e) => {
        setBookRest(e)
    }
    const onChangePicture = async (e) => {
        setPicture(e.target.files[0]);
    }

    function showModal() {
        if (ModalShow) {
            return (<>
                <Modal
                    open={ModalShow}
                    onOk={BookEdit}
                    onCancel={closeModal}
                    afterClose={closeModal}
                    okText={"提交"}
                    cancelText={"取消"}
                    okType={"default"}

                >
                    <div class="text-2xl">编辑书籍</div>
                    <form className="px-10 py-5">
                        <div>书名</div>
                        <input id="editbookname" type="text"
                               className="h-8 w-full border-solid border-2 my-3 px-2 rounded"
                               placeholder={"bookname"} value={BookTitle} onChange={onChangeTitle}></input><br/>
                        <div>ISBN</div>
                        <input id="editbookname" type="text"
                               className="h-8 w-full border-solid border-2 my-3 px-2 rounded"
                               placeholder={"ISBN"} value={BookISBN} onChange={onChangeISBN}></input><br/>
                        <div>原价</div>
                        <InputNumber onChange={onChangeOriginprice} min={0} step={0.01}
                                     defaultValue={BookOriginprice}></InputNumber>
                        <br/>
                        <div>现价</div>
                        <InputNumber onChange={onChangePrice} min={0} step={0.01}
                                     defaultValue={BookPrice}></InputNumber>
                        <br/>
                        <div>简介</div>
                        <input id="editintroduction" type="text"
                               className="h-8 w-full border-solid border-2 my-3 px-2 rounded"
                               placeholder={"introduction"} value={BookIntroduction}
                               onChange={onChangeIntroduction}></input><br/>
                        <div>作者</div>
                        <input id="editauthor" type="text"
                               className="h-8 w-full border-solid border-2 my-3 px-2 rounded"
                               placeholder={"author"} value={BookAuthor} onChange={onChangeAuthor}></input><br/>
                        <div>剩余</div>
                        <InputNumber onChange={onChangeRest} min={0} step={1}
                                     defaultValue={BookRest}></InputNumber>
                        <div>图片（不选择即为原图）</div>
                        <input id="editpic" type="file"
                               className="flex text-center items-center h-12 w-full border-solid border-2 my-3 px-2 rounded"
                               onChange={onChangePicture}
                               placeholder={"pic"}></input>
                        <br/>
                    </form>

                </Modal>
            </>)
        }
    }

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
            setBookList(result.data);
        } else {
            alert(result.msg)
        }
        setLoading(false)
    }
const showList=(e)=>{
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
            {showModal()}
            <div class="text-3xl py-5">书籍管理</div>
            <div class="text-xl">添加书籍</div>
            <form class="px-10 py-5 mb-9 border border-solid border-gray-400 rounded-2xl w-auto">
                <div>书名</div>
                <input id="bookname" type="text" className="h-8 w-auto border-solid border-2 my-3 px-2 rounded"
                       placeholder={"bookname"}></input><br/>
                <div>ISBN</div>
                <input id="isbn" type="text" className="h-8 w-auto border-solid border-2 my-3 px-2 rounded"
                       placeholder={"ISBN"}></input><br/>
                <div>原价</div>
                <InputNumber onChange={onChangeOriginprice} min={0} step={0.01}
                             placeholder={"originprice"}></InputNumber><br/>
                <div>现价</div>
                <InputNumber onChange={onChangePrice} min={0} step={0.01} placeholder={"price"}></InputNumber><br/>
                <div>简介</div>
                <input id="introduction" type="text" className="h-8 w-auto border-solid border-2 my-3 px-2 rounded"
                       placeholder={"introduction"}></input><br/>
                <div>作者</div>
                <input id="author" type="text" className="h-8 w-auto border-solid border-2 my-3 px-2 rounded"
                       placeholder={"author"}></input><br/>
                <div>剩余</div>
                <InputNumber onChange={onChangeRest} min={0} step={1} placeholder={"rest"}></InputNumber><br/>
                <div>图片</div>
                <input id="pic" type="file"
                       className="flex text-center items-center h-12 w-auto border-solid border-2 my-3 px-2 rounded"
                       onChange={handlePicture}
                       placeholder={"pic"}></input><br/>
                <button type={"button"} onClick={BookSubmit}
                        class="focus:outline-none text-sm w-auto py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-6 justify-center items-center text-center gap-5">提交
                </button>
            </form>
            <div class="text-xl">书籍数据修改</div>
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
export default BookManagement