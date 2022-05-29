import bcrypt from "bcrypt";
import User from "../../modules/model/User.js";

export async function createInitialData(){
    try {
        await User.sync({force: true});

        let password = await bcrypt.hash("123456", 10);
    
        await User.create({
            name: "User teste",
            email: "teste@gmail.com",
            password: password,
        });
    } catch (error) {
        console.error(`Error when: ${error}`);
    }
}
