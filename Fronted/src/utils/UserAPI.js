import {backendLink} from "./config";

export const NewUser = async (payload) => {
    try {
        console.log("new user");
        const response = await fetch(backendLink + "/newUser", {
            method: "POST",
            credentials: "include",
            body: payload,
        });
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error("请求失败");
        }
    } catch (error) {
        alert("请求发送失败");
    }
};


export const FindUserByEmail = async (email) => {
    try {
        const response = await fetch(backendLink + "/findUserByemail", {
            method: "POST",
            credentials: "include",
            body: email,
        });
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error("请求失败");
        }
    } catch (error) {
        alert("请求发送失败");
    }
};

export const FindUserByUserId = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/findUserByuserId", {
            method: "POST",
            credentials: "include",
            body: formdata,
        });
        if (response.ok) {
            return await response.json();
        } else {
            throw new Error("请求失败");
        }
    } catch (error) {
        alert("请求发送失败");
    }
};

export const GetStatistics = async (formdata) => {
    try {
        const response = await fetch(backendLink + "/getStatistics", {
            method: "POST",
            credentials: "include",
            body:formdata,
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("获取统计信息失败");
        }
    } catch (error) {
        throw new Error("获取统计信息失败", error)
    }
}
export const EditType=async (formdata) => {
    try {
        const response = await fetch(backendLink + "/editType", {
            method: "POST",
            credentials: "include",
            body:formdata,
        })
        if (response.ok) {
            return await response.json();
        } else {
            alert("编辑失败");
        }
    } catch (error) {
        throw new Error("编辑失败", error)
    }
}

