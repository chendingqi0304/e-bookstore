import {backendLink} from "./config";

export const GetIdentifyCode = async (email) => {
    try {
        const response = await fetch(backendLink + "/getIdentifyCode", {
            method: "POST",
            body: email,
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("发送失败");
        }
    } catch (error) {
        alert("请求发送失败");
    }
}
export const Identify = async (email, code) => {
    try {
        const formData = new FormData();
        formData.append("email", email);
        formData.append("code", code);
        const response = await fetch(backendLink + "/Identify", {
            method: "POST",
            body: formData,
        })
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error("请求失败");
        }
    } catch (error) {
        alert("请求发送失败");
    }
}