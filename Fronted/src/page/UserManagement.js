import Header from "../components/header";
import React from "react";
import {GetBook} from "../utils/BookAPI";
import {EditType, FindUserByUserId} from "../utils/UserAPI";

const UserManagement = () => {
    const [user, setUser] = React.useState(null);
    const [showEditor, setShowEditor] = React.useState(false);
    const handleSearch = async () => {
        const userId = document.getElementById("search").value;
        if (userId == null) {
            alert("请输入userId")
            return
        }
        console.log(userId)
        const formdata = new FormData();
        formdata.append("userId", parseInt(userId));
        const result = await FindUserByUserId(formdata);
        if (result.code === 1) {
            setUser(result.data);
            setShowEditor(true)
        } else {
            alert(result.msg)
        }
    }
    const handleEdit = async () => {
        const formdata = new FormData();
        const type = document.getElementById("selector").value;
        console.log(type)
        formdata.append("userId", parseInt(user.userId));
        formdata.append("type", parseInt(type));
        if (type === user.type) {
            alert("与原权限相同");
            return;
        } else {
            const result = await EditType(formdata)
            if (result.code === 1) {
                alert("修改成功")
                window.location.reload();
            } else {
                alert(result.msg)
            }
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
        <div className="mx-24 mt-8">
            <div class="text-3xl">用户管理</div>
            <div className="flex items-center">
                <input type="text" className="my-3 mx-3 border border-solid rounded-md" id="search"
                       placeholder={"搜索userId"}></input>
                <button onClick={handleSearch}
                        className="focus:outline-none text-sm w-auto py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-6 justify-center items-center text-center gap-5">搜索
                </button>
            </div>
            {showEditor && <>
                <div>userId：{user.userId}</div>
                <div>账号：{user.account}</div>
                <div>用户名：{user.username}</div>
                <select className="border border-gray-300 rounded-md px-1 ml-2 h-8 text-sm my-3" id="selector">
                    <option label="用户权限" value="-2" selected hidden/>
                    <option label="黑名单用户" value="-1"/>
                    <option label="普通用户" value="0"/>
                    <option label="管理员" value="1"/>
                </select>
                <button type={"button"} onClick={handleEdit}
                        className="focus:outline-none text-sm w-auto mt-3 py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-6 justify-center items-center text-center gap-5">
                    确认修改权限
                </button>
            </>}
        </div>
        </body>
        </html>
    </>
}
export default UserManagement;