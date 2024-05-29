import {backendLink} from "./config";

export const Login=async (payload)=>{
    try{
        const response = await fetch(backendLink+"/login", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            credentials: "include",
            body: JSON.stringify(payload)
        });
        if (response.ok) {
            return  await response.json();
        } else {
            throw new Error("请求失败");
        }
    }
    catch (error){
        alert("请求发送失败");
    }
}