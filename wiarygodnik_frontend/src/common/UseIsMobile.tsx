import { useEffect, useState } from "react";

export function useIsMobile() {
    const query = "(max-width: 800px)";
    const mql = window.matchMedia(query);

    const [isMobile, setIsMobile] = useState(mql.matches);

    useEffect(() => {
        const handler = (e: MediaQueryListEvent) => {
            setIsMobile(e.matches);
        };

        mql.addEventListener("change", handler);
        return () => mql.removeEventListener("change", handler);
    }, []);

    return isMobile;
}
