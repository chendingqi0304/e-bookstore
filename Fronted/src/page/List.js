import Header from "../components/header";
import React, {useEffect, useState} from "react";
import {BookStatisticsList, UserStatisticsList,} from "../utils/OrderAPI";
import Chart from "../components/Chart";


const List = () => {
    const [loading, SetLoading] = useState(true);
    const [User_x_data, SetUser_x_data] = useState([]);
    const [User_y_data, SetUser_y_data] = useState([]);
    const [Book_x_data, SetBook_x_data] = useState([]);
    const [Book_y_data, SetBook_y_data] = useState([]);
    const user_options = {
        tooltip: {},
        legend: {
            data: ["用户消费榜"],
        },
        xAxis: {
            data: User_x_data,
            axisLabel: {
                interval: 0,
            }
        },
        yAxis: {},
        series: [
            {
                name: "购买量",
                type: "bar",
                data: User_y_data,
                label: {
                    show: true,
                    position: "top",
                    formatter: "{c}"
                }
            },
        ],
    };
    const book_options = {
        tooltip: {},
        legend: {
            data: ["图书销量榜"],
        },
        xAxis: {
            data: Book_x_data,
            axisLabel: {
                interval: 0,
            }
        },
        yAxis: {},
        series: [
            {
                name: "购买量",
                type: "bar",
                data: Book_y_data,
                label: {
                    show: true,
                    position: "top",
                    formatter: "{c}"
                }
            },
        ],
    }

    const fetchUserList = async (time) => {
        const formdata = new FormData
        formdata.append("time", time)
        const result = await UserStatisticsList(formdata);
        if (result.code === 1) {
            console.log(result.data);
            let x_data = [];
            let y_data = [];
            result.data.map((item) => {
                x_data.push(item.username);
                y_data.push(item.moneyCount / 100);
            })
            SetUser_x_data(x_data);
            SetUser_y_data(y_data);
        } else {
            alert(result.msg)
        }
    }
    const fetchBookList = async (time) => {
        const formdata = new FormData
        formdata.append("time", time)
        const result = await BookStatisticsList(formdata);
        if (result.code === 1) {
            console.log(result.data);
            let x_data = [];
            let y_data = [];
            result.data.map((item) => {
                x_data.push(item.name);
                y_data.push(item.number);
            })
            SetBook_x_data(x_data);
            SetBook_y_data(y_data);
        } else {
            alert(result.msg)
        }
    }

    useEffect(() => {
        fetchUserList(1).then(r => {
            fetchBookList(1).then(r => {
                SetLoading(false)
            });
        });
    }, []);

    const handleSubmit = async () => {
        const timevalue = document.getElementById("selector").value;
        SetLoading(true);
        await fetchBookList(timevalue)
        await fetchUserList(timevalue)
        SetLoading(false)
    }

    const ShowChart = (e) => {
        if (loading) return (<></>);
        else return (
            <>
                <div className="text-2xl pl-20 py-5">
                    消费榜
                </div>

                <div style={{textAlign: "center"}}>
                    <Chart options={user_options}/>
                </div>
                <div className="text-2xl pl-20 py-5">
                    销量榜
                </div>
                <div style={{textAlign: "center"}}>
                    <Chart options={book_options}/>
                </div>
            </>
        )
    }


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
            <select className="border border-gray-300 rounded-md px-1 ml-2 h-8 text-sm my-3" id="selector">
                <option label="一月内" value="0" selected/>
                <option label="一周内" value="1"/>
                <option label="一天内" value="2"/>
            </select>
            <button type={"button"} onClick={handleSubmit}
                    className="focus:outline-none text-sm w-auto mt-3 py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-6 justify-center items-center text-center gap-5">
                确定
            </button>
            {ShowChart(loading)}

        </div>
        </body>
        </html>
    </>
}
export default List