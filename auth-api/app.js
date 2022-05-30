import express from "express";
import * as db from "./src/config/db/initialData.js"; // importa funções do módulo
import userRouter from "./src/modules/router/UserRouter.js";
import checkToken from "./src/config/auth/checkToken.js";

const app = express();
const env = process.env;
const PORT = env.PORT || 8080;

db.createInitialData();

app.get('/api/status', (req, res) => {
    return res.status(200).json({
        service: 'Auth-api',
        status: 'up',
        httpStatus: 200
    });
});

app.use(express.json())
app.use(express.urlencoded({ extended: true} ));
app.use(userRouter);

app.use(checkToken);

app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
});
