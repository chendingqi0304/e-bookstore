import {wsLink} from "./config";
import {useEffect, useRef} from "react";


let socketConnection = null;
export const SocketConnect = async (onMessageCallback) => {
    const account = sessionStorage.getItem('account');
    const socket = new WebSocket(wsLink + "/"+JSON.parse(account).userId);

    // 连接打开时执行的回调
    socket.onopen = () => {
        console.log("WebSocket 连接成功");
    };

    // 接收消息时执行的回调
    socket.onmessage = (event) => {
        console.log("接收到消息:", event.data);
        if (onMessageCallback) {
            onMessageCallback(event.data);
        }
        socket.close();
    };

    // 连接关闭时执行的回调
    socket.onclose = () => {
        console.log("WebSocket 连接关闭");
    };

    // 发生错误时执行的回调
    socket.onerror = (error) => {
        console.error("WebSocket 发生错误:", error);
    };
}

// // 发送消息
// export const sendMessage = (message) => {
//     if (socketConnection.current && socketConnection.current.readyState === WebSocket.OPEN) {
//         socketConnection.current.send(message);
//     } else {
//         console.error("WebSocket 未连接或已关闭");
//     }
// };

