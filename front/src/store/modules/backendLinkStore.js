import {createSlice} from "@reduxjs/toolkit";


const storedBackendLink = sessionStorage.getItem('backendLink');

const backendLink =storedBackendLink? storedBackendLink:"http://localhost:8080";//空账号为0

const initialState = {
    backendLink:backendLink
}

const backendLinkSlice = createSlice(

    {
        name: 'backendLink',
        initialState,
        reducers:{

        }
    }
);

export default backendLinkSlice.reducer;