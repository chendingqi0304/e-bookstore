import {backendLink} from "./config";

export const AllBookList = async () => {
    try {
        const response = await fetch(backendLink + "/Allbooklist", {
            method: "POST",
            credentials: "include",
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取书籍列表失败");
        }
    } catch (error) {
        throw new Error("获取书籍列表失败", error)
    }
}
export const BookList = async () => {
    try {
        const response = await fetch(backendLink + "/booklist", {
            method: "POST",
            credentials: "include",
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取书籍列表失败");
        }
    } catch (error) {
        throw new Error("获取书籍列表失败", error)
    }
}
export const AddBook = async (formdata) => {
    try {
        var response = await fetch(backendLink + "/addbook", {
            method: "POST",
            body: formdata,
        })
        if (!response.ok) {
            alert("error")
        } else {
            alert("上传成功")
            window.location.reload();
        }
    } catch (error) {
        throw new Error("添加失败", error)
    }
}

export const GetBook = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/getBook", {
            method: "POST",
            body: formdata,
            credentials: "include"
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取书籍信息失败")
        }
    } catch (error) {
        throw new Error("获取书籍信息失败", error)
    }
}
export const DeleteBook = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/deleteBook", {
            method: 'POST',
            credentials: "include",
            body: formdata,
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("删除失败")
        }
    } catch (error) {
        throw new Error("删除失败", error)
    }
}
export const RecoverBook = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/recoverBook", {
            method: 'POST',
            credentials: "include",
            body: formdata,
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("恢复失败")
        }
    } catch (error) {
        throw new Error("恢复失败", error)
    }
}
export const EditBook = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/editBook", {
            method: "POST",
            body: formdata,
            credentials: "include",
        })
        if (response.ok) {
            return await response.json();
        }
    } catch (error) {
        throw new Error("编辑失败", error)
    }
}