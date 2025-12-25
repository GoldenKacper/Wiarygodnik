import { Routes, Route, BrowserRouter } from "react-router-dom";
import Raports from "../pages/Raports";
import ProtectedRoutes from "./ProtectedRoutes";
import NotFound from "../pages/NotFound";
import User from "../pages/User";
import { Home } from "../pages/Home";

export default function AppRouter() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/" element={<Home />} />
                <Route element={<ProtectedRoutes roles={["user"]} />}>
                    <Route path="/raports" element={<Raports />} />
                    <Route path="/user" element={<User />} />
                </Route>
                <Route path="*" element={<NotFound />} />
            </Routes>
        </BrowserRouter>
    );
}
