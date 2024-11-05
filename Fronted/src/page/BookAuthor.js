import Header from "../components/header";
import React, {useState} from "react";
import {getBookAuthor} from "../utils/BookAuthorAPI";


const BookAuthor = () => {
    const [author, setAuthor] = useState(null);

    const handleSearch = async () => {
        const title=document.getElementById("search").value
        if (title === null) {
            alert("请输入书名")
            return
        }
        const result = await getBookAuthor(title);
        if (result.code === 1) {
            setAuthor(result.data)
        }else{
            setAuthor(null)
            alert(result.msg)
        }
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
            <div class="text-3xl py-5">书名查询作者</div>

            <div class="flex items-center">
                <input type="text" className="my-3 mx-3 border border-solid rounded-md" id="search"
                       placeholder={"输入书名"}></input>
                <button onClick={handleSearch}
                        class="focus:outline-none text-sm w-auto py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-6 justify-center items-center text-center gap-5">搜索
                </button>
            </div>
            {author && <p>书籍作者为: {author}</p>}

        </div>

        </body>
        </html>
    </>

}
export default BookAuthor;