import Header from "../components/header";
import React, {useEffect, useRef} from 'react';
import {useState} from "react";
import {PlusOutlined} from '@ant-design/icons';
import {LoadingOutlined} from '@ant-design/icons';
import {message, Statistic} from 'antd';
import {
    DatePicker,
    Form,
    Input,
    InputNumber,
    Mentions,
    Select,
} from 'antd';
import Chart from "../components/Chart";
import {GetStatistics} from "../utils/UserAPI";


const getBase64 = (img, callback) => {
    const reader = new FileReader();
    reader.addEventListener('load', () => callback(reader.result));
    reader.readAsDataURL(img);
};
const beforeUpload = (file) => {
    const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png';
    if (!isJpgOrPng) {
        message.error('You can only upload JPG/PNG file!');
    }
    const isLt2M = file.size / 1024 / 1024 < 2;
    if (!isLt2M) {
        message.error('Image must smaller than 2MB!');
    }
    return isJpgOrPng && isLt2M;
};
const {RangePicker} = DatePicker;

const formItemLayout = {
    labelCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 6,
        },
    },
    wrapperCol: {
        xs: {
            span: 24,
        },
        sm: {
            span: 14,
        },
    },
};
const PersonalPage = () => {
    const [loading, SetLoading] = useState(true);
    const [Xdata, SetXdata] = useState([]);
    const [Ydata, SetYdata] = useState([]);
    const [bookCount, setBookCount] = useState(0);
    const [moneyCount, setMoneyCount] = useState(0);
    const options = {
        tooltip: {},
        legend: {
            data: ["销量"],
        },
        xAxis: {
            data: Xdata,
            axisLabel:{
                interval: 0,
            }
        },
        yAxis: {},
        series: [
            {
                name: "购买量",
                type: "bar",
                data: Ydata,
                label:{
                    show: true,
                    position: "top",
                    formatter:"{c}"
                }
            },
        ],
    };

    useEffect( () => {
        const fetchStatistics = async ()=>{
            const result = await GetStatistics()
            if(result.code===1){
                let x_data = [];
                let y_data = [];

                result.data.orderItems.map(item => {
                    x_data.push(item.name);
                    y_data.push(item.number);
                })
                SetXdata(x_data);
                SetYdata(y_data);
                setBookCount(result.data.bookCount);
                setMoneyCount(result.data.moneyCount/100);
                SetLoading(false);
            }else {
                alert(result.msg)
            }
        }
        fetchStatistics().then();
    }, [])
    if (loading) return (<></>);
    return (
        <>
            <html lang="en">
            <head>
                <meta charset="UTF-8"/>
                <title>E-BookStore</title>
                <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
                <link href="../dist/output.css" rel="stylesheet"/>
            </head>
            <body>
            <Header/>
            <div className="text-3xl pl-20 py-5">
                个人主页
            </div>
            <div className="text-2xl pl-20 py-5">
                一月内订单情况
            </div>
            <div style={{textAlign: "center"}}>
                <Chart options={options}/>
                <div className="flex items-center justify-center">
                    <div className="mx-3"><Statistic title="购书数目" value={bookCount}/></div>
                    <div className="mx-3"><Statistic title="金额总计" value={moneyCount} precision={2} prefix={"￥"}/></div>
                </div>

            </div>
            {/*<div className="pt-10 flex justify-center w-full">*/}

            {/*    <Form*/}
            {/*        {...formItemLayout}*/}
            {/*        variant="filled"*/}
            {/*        className="w-full"*/}
            {/*    >*/}
            {/*        <Form.Item*/}
            {/*            label="头像"*/}
            {/*            name="avatar"*/}
            {/*            rules={[*/}
            {/*                {*/}
            {/*                    required: true,*/}
            {/*                    message: 'Please input!',*/}
            {/*                },*/}
            {/*            ]}>*/}
            {/*            <Upload*/}
            {/*                name="avatar"*/}
            {/*                listType="picture-circle"*/}
            {/*                className="avatar-uploader w-full"*/}
            {/*                showUploadList={false}*/}
            {/*                action=" https://660d2bd96ddfa2943b33731c.mockapi.io/api/upload"*/}
            {/*                beforeUpload={beforeUpload}*/}
            {/*                onChange={handleChange}*/}
            {/*            >*/}
            {/*                {imageUrl ? (*/}
            {/*                    <img*/}
            {/*                        src={imageUrl}*/}
            {/*                        alt="avatar"*/}
            {/*                        style={{*/}
            {/*                            width: '100%',*/}
            {/*                        }}*/}
            {/*                    />*/}
            {/*                ) : (*/}
            {/*                    uploadButton*/}
            {/*                )}*/}
            {/*            </Upload>*/}

            {/*        </Form.Item>*/}
            {/*        <Form.Item*/}
            {/*            label="昵称"*/}
            {/*            name="name"*/}
            {/*            rules={[*/}
            {/*                {*/}
            {/*                    required: true,*/}
            {/*                    message: 'Please input!',*/}
            {/*                },*/}
            {/*            ]}*/}
            {/*        >*/}
            {/*            <Input placeholder={"饮冰雪之人"}/>*/}
            {/*        </Form.Item>*/}
            {/*        <Form.Item*/}
            {/*            label="电话"*/}
            {/*            name="phone"*/}
            {/*            rules={[*/}
            {/*                {*/}
            {/*                    required: true,*/}
            {/*                    message: 'Please input!',*/}
            {/*                },*/}
            {/*            ]}*/}
            {/*        >*/}
            {/*            <InputNumber*/}
            {/*                placeholder={"13333333333"}*/}
            {/*                style={{*/}
            {/*                    width: '100%',*/}
            {/*                }}*/}
            {/*            />*/}
            {/*        </Form.Item>*/}
            {/*        <Form.Item*/}
            {/*            label="性别"*/}
            {/*            name="gender"*/}
            {/*            rules={[*/}
            {/*                {*/}
            {/*                    required: false,*/}
            {/*                    message: 'Please input!',*/}
            {/*                },*/}
            {/*            ]}*/}
            {/*        >*/}
            {/*            <Select placeholder={"沃尔玛购物袋"} options={[*/}
            {/*                {*/}
            {/*                    value: 'man',*/}
            {/*                    label: '男',*/}
            {/*                },*/}
            {/*                {*/}
            {/*                    value: 'woman',*/}
            {/*                    label: '女',*/}
            {/*                }*/}
            {/*            ]}/>*/}
            {/*        </Form.Item>*/}
            {/*        <Form.Item*/}
            {/*            label="邮箱"*/}
            {/*            name="mail"*/}
            {/*            rules={[*/}
            {/*                {*/}
            {/*                    required: false,*/}
            {/*                    message: 'Please input!',*/}
            {/*                },*/}
            {/*            ]}*/}
            {/*        >*/}
            {/*            <Mentions placeholder={"dingk@sjtu.edu.cn"}/>*/}
            {/*        </Form.Item>*/}
            {/*        <Form.Item*/}
            {/*            label="出生日期"*/}
            {/*            name="birthday"*/}
            {/*            rules={[*/}
            {/*                {*/}
            {/*                    required: false,*/}
            {/*                    message: 'Please input!',*/}
            {/*                },*/}
            {/*            ]}*/}
            {/*        >*/}
            {/*            <DatePicker/>*/}
            {/*        </Form.Item>*/}
            {/*        <Form.Item*/}
            {/*            wrapperCol={{*/}
            {/*                offset: 6,*/}
            {/*                span: 16,*/}
            {/*            }}*/}
            {/*        >*/}
            {/*            <button*/}
            {/*                className="focus:outline-none text-sm py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-12 w-20 justify-center items-center text-center gap-5"*/}
            {/*                type="submit">*/}

            {/*                <span>提交</span>*/}
            {/*            </button>*/}
            {/*        </Form.Item>*/}
            {/*    </Form>*/}
            {/*</div>*/}
            </body>
            </html>
        </>
    )
}
export default PersonalPage