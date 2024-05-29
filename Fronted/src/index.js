//项目从这里开始运行

//导入项目核心包
import React from 'react';
import ReactDOM from 'react-dom/client';
import "reset-css"
import store from "./store";

import App from './App';
import {Provider} from "react-redux";

//把App根组件渲染到id为root的DOM节点上
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
    <React.StrictMode>
        <Provider store={store}>
            <App/>
        </Provider>
    </React.StrictMode>
);
