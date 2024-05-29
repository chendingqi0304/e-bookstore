import {backendLink} from "./config";

export const GetCart = async () => {
    try {
        const response = await fetch(backendLink + "/getCart", {
            method: "POST",
            credentials: "include"
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取购物车列表失败");
        }
    } catch (error) {
        throw new Error("获取购物车列表失败", error)
    }
}
export const UpdateCart = async (formdata) => {
    const response = await fetch(backendLink + "/updateCartNum", {
        method: "POST",
        credentials: "include",
        body: formdata,
    })
    if (!response.ok) {
        alert("error");
    }
}
export const AddCartByBookId = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/addCartbyBookId", {
            method: "POST",
            body: formdata,
            credentials: "include"
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("添加购物车失败")
        }
    } catch (error) {
        throw new Error("添加购物车失败", error)
    }
}