import Header from "../components/header";
import React from "react";
import {useState} from "react";

const DataManagement = () => {
    const [picture, setPicture] = React.useState("");
    const handlePicture = (e) => {
        setPicture(e.target.files[0]);
    }
    const BookSubmit=async ()=>{
        var bookname=document.getElementById("bookname").value;
        var price=document.getElementById("price").value;
        var introduction=document.getElementById("introduction").value;
        var author=document.getElementById("author").value;
        console.log(picture);
        var formdata=new FormData();
        formdata.append("bookname",bookname);
        formdata.append("price",price);
        formdata.append("introduction",introduction);
        formdata.append("author",author);
        formdata.append("picture",picture);
        var response=await fetch("http://localhost:8080/addbook",{
            method: "POST",
            body: formdata,
        })
        if (!response.ok) {
            alert("error")
        }else {
            alert("上传成功")
            window.location.reload();
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
        <Header></Header>
        <div>数据库管理</div>
        <div>添加书籍</div>
        <form class="px-10 py-5">
            <input id="bookname" type="text" className="h-8 w-auto border-solid border-2"
                   placeholder={"bookname"}></input><br/>
            <input id="price" type="number" className="h-8 w-auto border-solid border-2"
                   placeholder={"price"}></input><br/>
            <input id="introduction" type="text" className="h-8 w-auto border-solid border-2"
                   placeholder={"introduction"}></input><br/>
            <input id="author" type="text" className="h-8 w-auto border-solid border-2"
                   placeholder={"author"}></input><br/>
            <input id="pic" type="file" className="h-8 w-auto border-solid border-2"
                   onChange={handlePicture}
                   placeholder={"pic"}></input><br/>
            <button type={"button"} onClick={BookSubmit}>提交</button>
        </form>
        </body>
        </html>
    </>
}
export default DataManagement