import {backendLink} from "./config";

export const GetOrder = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/getOrder", {
            method: "POST",
            credentials: "include",
            body: formdata,
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
            return await response.json();
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

export const UserStatisticsList = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/UserStatisticsList", {
            method: "POST",
            credentials: "include",
            body: formdata,
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取用户统计信息失败")
        }
    } catch (error) {
        throw new error("获取用户统计信息失败，error")
    }
}
export const BookStatisticsList = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/BookStatisticsList", {
            method: "POST",
            credentials: "include",
            body: formdata,
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取图书统计信息失败")
        }
    } catch (error) {
        throw new error("获取图书统计信息失败，error")
    }
}
export const GetSelectedOrder = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/getSelectedOrder", {
            method: "POST",
            credentials: "include",
            body: formdata,
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

export const GetAllOrder = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/getAllOrder", {
            method: "POST",
            body: formdata,
            credentials: "include",

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

export const GetAllSelectedOrder = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/getAllSelectedOrder", {
            method: "POST",
            credentials: "include",
            body: formdata,
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