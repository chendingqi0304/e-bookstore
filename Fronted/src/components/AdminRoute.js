// src/components/PrivateRoute.jsx
import React from 'react';
import { Navigate } from 'react-router-dom';
import {useSelector} from "react-redux";

const PrivateRoute = ({ children }) => {
    const account=JSON.parse((useSelector((state)=>state.account)).accountInfo);
    if (account!=null&&account.type!==1) {
        return <Navigate to="/login" />;
    }
    return children;
};

export default PrivateRoute;