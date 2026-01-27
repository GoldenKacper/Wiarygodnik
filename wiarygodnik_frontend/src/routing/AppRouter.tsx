import { Routes, Route, BrowserRouter } from "react-router-dom";
import Reports from "../pages/Reports";
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
                    <Route path="/reports" element={<Reports />}>
                        <Route path=":requestId" element={<Reports />} />
                    </Route>
                    <Route path="/user" element={<User />} />
                </Route>
                <Route path="*" element={<NotFound />} />
            </Routes>
        </BrowserRouter>
    );
}
