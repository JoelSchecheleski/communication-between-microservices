import User from "../model/User.js"

class UserRepository {

    async findById(id) {
        try {
            await User.findById({where: { id }})
        }catch (e) {
            console.error(e.message);
            return e
        }
    }

    async findByEmail(email) {
        try {
            return await User.findOne({where: { email }});
        } catch (e) {
            console.error(e.message);
            return e
        }
    }
}

export default new UserRepository();
