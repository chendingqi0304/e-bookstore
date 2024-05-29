import Header from "../components/header";
import React from 'react';
import {useState} from "react";
import {PlusOutlined} from '@ant-design/icons';
import {LoadingOutlined} from '@ant-design/icons';
import {message} from 'antd';
import {Upload} from 'antd';

import {
    DatePicker,
    Form,
    Input,
    InputNumber,
    Mentions,
    Select,
} from 'antd';


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

    const [loading, setLoading] = useState(false);
    const [imageUrl, setImageUrl] = useState();
    const handleChange = (info) => {
        if (info.file.status === 'uploading') {
            setLoading(true);
            return;
        }
        if (info.file.status === 'done') {
            // Get this url from response in real world.
            getBase64(info.file.originFileObj, (url) => {
                setLoading(false);
                setImageUrl(url);
            });
        }
    };
    const uploadButton = (
        <button
            style={{
                border: 0,
                background: 'none',
            }}
            type="button"
        >
            {loading ? <LoadingOutlined/> : <PlusOutlined/>}
            <div
                style={{
                    marginTop: 8,
                }}
            >
                Upload
            </div>
        </button>
    );
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
            <div className="pt-10 flex justify-center w-full">

                <Form
                    {...formItemLayout}
                    variant="filled"
                    className="w-full"
                >
                    <Form.Item
                        label="头像"
                        name="avatar"
                        rules={[
                            {
                                required: true,
                                message: 'Please input!',
                            },
                        ]}>
                        <Upload
                            name="avatar"
                            listType="picture-circle"
                            className="avatar-uploader w-full"
                            showUploadList={false}
                            action=" https://660d2bd96ddfa2943b33731c.mockapi.io/api/upload"
                            beforeUpload={beforeUpload}
                            onChange={handleChange}
                        >
                            {imageUrl ? (
                                <img
                                    src={imageUrl}
                                    alt="avatar"
                                    style={{
                                        width: '100%',
                                    }}
                                />
                            ) : (
                                uploadButton
                            )}
                        </Upload>

                    </Form.Item>
                    <Form.Item
                        label="昵称"
                        name="name"
                        rules={[
                            {
                                required: true,
                                message: 'Please input!',
                            },
                        ]}
                    >
                        <Input placeholder={"饮冰雪之人"}/>
                    </Form.Item>
                    <Form.Item
                        label="电话"
                        name="phone"
                        rules={[
                            {
                                required: true,
                                message: 'Please input!',
                            },
                        ]}
                    >
                        <InputNumber
                            placeholder={"13333333333"}
                            style={{
                                width: '100%',
                            }}
                        />
                    </Form.Item>
                    <Form.Item
                        label="性别"
                        name="gender"
                        rules={[
                            {
                                required: false,
                                message: 'Please input!',
                            },
                        ]}
                    >
                        <Select placeholder={"沃尔玛购物袋"} options={[
                            {
                                value: 'man',
                                label: '男',
                            },
                            {
                                value: 'woman',
                                label: '女',
                            }
                        ]}/>
                    </Form.Item>
                    <Form.Item
                        label="邮箱"
                        name="mail"
                        rules={[
                            {
                                required: false,
                                message: 'Please input!',
                            },
                        ]}
                    >
                        <Mentions placeholder={"dingk@sjtu.edu.cn"}/>
                    </Form.Item>
                    <Form.Item
                        label="出生日期"
                        name="birthday"
                        rules={[
                            {
                                required: false,
                                message: 'Please input!',
                            },
                        ]}
                    >
                        <DatePicker/>
                    </Form.Item>
                    <Form.Item
                        wrapperCol={{
                            offset: 6,
                            span: 16,
                        }}
                    >
                        <button
                            className="focus:outline-none text-sm py-3 rounded-md font-semibold text-white bg-blue-500 ring-4 flex p-4 h-12 w-20 justify-center items-center text-center gap-5"
                            type="submit">

                            <span>提交</span>
                        </button>
                    </Form.Item>
                </Form>
            </div>
            </body>
            </html>
        </>
    )
}
export default PersonalPage