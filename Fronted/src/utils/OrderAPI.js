import {backendLink} from "./config";

export const GetOrder = async () => {
    try {
        const response = await fetch(backendLink + "/getOrder", {
            method: "POST",
            credentials: "include"
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取订单列表失败");
        }
    } catch (error) {
        throw new Error("获取订单列表失败", error)
    }
}

export const BuyByCartId = async (formdata) => {
    try {
        const response = await fetch("http://localhost:8080/BuybyCartIds", {
            method: "POST",
            body: formdata,
            credentials: "include"
        })
        if (response.ok) {

        } else {
            alert("提交订单失败");
        }
    } catch (error) {
        throw new Error("提交订单失败", error)
    }
}
export const BuyByBookId = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/BuybyBookId", {
            method: "POST",
            body: formdata,
            credentials: "include"
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("提交订单失败")
        }
    } catch (error) {
        throw new Error("提交订单失败", error)
    }
}