import Header from "../components/header";
import React from "react";

const List=()=>{
    return <>
        <html lang="en">
        <head>
            <meta charset="UTF-8"/>
            <title>E-BookStore</title>
            <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
            <link href="../dist/output.css" rel="stylesheet"/>
        </head>
        <body>
        <Header></Header>
        <div className="mx-24 mt-8">
            <div class="text-3xl">查看表单</div>
        </div>
        </body>
        </html>
    </>
}
export default List