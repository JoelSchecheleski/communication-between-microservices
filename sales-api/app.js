import express from "express";

import {connect} from './src/config/db/mongoDbConfig.js'
import Order from "./src/modules/sales/model/Order.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8081;

connect();

app.get('/api/status', (req, res) => {
    let test = Order.find();
    console.log(test);


    return res.status(200).json({
        service: 'Sales-api',
        status: 'up',
        httpStatus: 200
    });
});

app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
});
