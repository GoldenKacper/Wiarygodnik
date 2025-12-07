import { Routes, Route } from 'react-router-dom'
import Home from './home/Home'
import User from "./user/User.tsx";

function App() {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/user" element={<User />} />
        </Routes>
    )
}

export default App