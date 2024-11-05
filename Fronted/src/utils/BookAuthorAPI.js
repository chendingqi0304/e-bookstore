import {microserviceLink} from "./config";

export const getBookAuthor = async (title) => {
    try {
        const response = await fetch(microserviceLink + `/author/BookAuthor?title=${title}`, {
            method: "GET",
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取书籍作者失败");
        }
    } catch (error) {
        throw new Error("获取书籍作者失败", error)
    }
}