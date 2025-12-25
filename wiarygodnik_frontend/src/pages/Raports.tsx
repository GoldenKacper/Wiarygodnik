import NavBar from "../navbar/NavBar.tsx";
import {Box, Button, CircularProgress, Divider, Input, Link, Typography} from "@mui/material";
import theme from "../theme.ts";
import {useState} from "react";
import SearchOutlinedIcon from '@mui/icons-material/SearchOutlined';
import {useIsMobile} from "../common/UseIsMobile.tsx";
import LightbulbIcon from '@mui/icons-material/Lightbulb';
import AutoAwesomeIcon from '@mui/icons-material/AutoAwesome';
import CircleIcon from '@mui/icons-material/Circle';
import ErrorIcon from '@mui/icons-material/Error';
import CancelIcon from '@mui/icons-material/Cancel';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import {sideMenuButton, sourceDot, sourceLink} from "../Style.tsx";
import {getDomain} from "../common/Scripts.tsx";
import ArticleIcon from '@mui/icons-material/Article';

function Raports() {
    const isMobile = useIsMobile();
    const sleep = (ms: number) => new Promise(resolve => setTimeout(resolve, ms));

    const [isContentLoaded, setIsContentLoaded] = useState(false);
    const [isContentLoading, setIsContentLoading] = useState(false);
    const [loadingStatus, setLoadingStatus] = useState("");
    const [value, setValue] = useState("");

    const [reports, setReports] = useState<string[]>(["Szaleństwo w Wiśle", "Przebudowa stadionu narodowego", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2", "raport 2"]);
    const [titleOfReport, setTitleOfReport] = useState<string>("Szaleństwo w Wiśle. Piotr Żyła walczył o wygraną!");
    const [analyzedSource, setAnalyzedSource] = useState<string>("https://sportowefakty.wp.pl/skoki-narciarskie/1223783/szalenstwo-w-wisle-piotr-zyla-walczyl-o-wygrana");
    const [foundSource, setFoundSources] = useState<string[]>(["https://eurosport.tvn24.pl/skoki-narciarskie/wisla/2025-2026/skoki-narciarskie-w-wisle.-domen-prevc-wygral-niedzielny-konkurs-piotr-zyla-i-kacper-tomasiak-w-pierwszej-dziesiatce_sto23248326/story.shtml", "https://przegladsportowy.onet.pl/sporty-zimowe/skoki-narciarskie/skoki-narciarskie-dzisiaj-ps-w-wisle-kwalifikacje-i-konkurs-indywidualny-na-zywo/cny6197", "https://sport.tvp.pl/90416055/o-ktorej-skoki-narciarskie-dzisiaj-na-zywo-konkurs-indywidualny-ps-wisla-w-niedziele-7-grudnia-2025", "https://www.sport.pl/skoki/7,65074,32453104,ps-w-wisle-o-ktorej-godzinie-skoki-w-niedziele-gdzie-ogladac.html"]);
    const [result, setResult] = useState<"HIGH" | "MID" | "LOW">("HIGH");
    const [menuActive, setMenuActive] = useState<boolean>(!isMobile);

    async function handleSearch() {
        navigator.vibrate?.(200);
        setIsContentLoaded(false);
        setIsContentLoading(true);
        setLoadingStatus("Szukanie innych źródeł");
        await sleep(2000);
        setLoadingStatus("Analizowanie innych źródeł");
        await sleep(1500);
        setLoadingStatus("Generowanie raportu");
        await sleep(1000);
        setIsContentLoaded(true);
        setIsContentLoading(false);
        navigator.vibrate?.(200);
    }

    function mapResult() {
        switch (result) {
            case "HIGH":
                return (
                    <Typography sx={{display: "flex", fontWeight: "bold", fontSize: "1.6rem", color: "#8dff5d"}}>
                        <CheckCircleIcon sx={{alignSelf: "center", fontSize: "2rem", marginRight: "5px"}} />
                        Źródło wiarygodne
                    </Typography>
                );

            case "MID":
                return (
                    <Typography sx={{display: "flex", fontWeight: "bold", fontSize: "1.6rem", color: "#faff5d"}}>
                        <ErrorIcon sx={{alignSelf: "center", fontSize: "2rem", marginRight: "5px"}} />
                        Źródło częściowo wiarygodne
                    </Typography>
                );

            case "LOW":
                return (
                    <Typography sx={{display: "flex", fontWeight: "bold", fontSize: "1.6rem", color: "#ff5d5d"}}>
                        <CancelIcon sx={{alignSelf: "center", fontSize: "2rem", marginRight: "5px"}}/>
                        Źródło niewiarygodne
                    </Typography>
                );

            default:
                return null;
        }
    }

    function sideMenu() {
        return (
            <Box
                sx={{
                    width: isMobile ? "100vw" : "280px",
                    minHeight: "calc(100vh - 100px)",
                    padding: "10px",
                    position: isMobile ? "fixed" : "relative",
                    top: isMobile ? "100px" : 0,
                    left: 0,
                    zIndex: 1000
                }}
            >
                <Box sx={{
                    minWidth: "100%",
                    minHeight: "100%",
                    backgroundColor: theme.palette.background.paper,
                    borderRadius: "15px",
                    padding: "10px",
                    boxShadow: "0 0 10px #181a20"
                }}>
                    <Typography sx={{
                        textAlign: "center",
                        fontWeight: "regular",
                        fontSize: "2rem",
                        display: "flex",
                    }}>
                        <ArticleIcon sx={{alignSelf: "center", marginRight: "15px", fontSize: "2rem",}}/>
                        Raporty
                    </Typography>
                    <Divider sx={{backgroundColor: theme.palette.text.secondary, my: "10px"}}/>
                    <Box sx={{
                        display: "flex",
                        height: "calc(100vh - 210px)",
                        flexDirection: "column",
                        overflowY: "auto",
                    }}>
                        {reports.map((report, index) => (
                            <Button
                                aria-label={report}
                                key={index}
                                sx={sideMenuButton}
                            >
                                {report}
                            </Button>
                        ))}
                    </Box>
                </Box>
            </Box>
        );
    }

    function content() {
        return (
            <Box sx={{display: "flex", flexDirection: isMobile ? "column" : "row", flex: 1, paddingBottom: "10px", gap: isMobile ? 0 : 4}}>
                <Box sx={{display: "flex", maxHeight: isMobile ? "calc(50vh - 100px)" : "calc(100vh - 210px)", flexDirection: "column", flex: 1, gap: 2, overflowY: "auto", overflowX: "hidden"}}>
                    <Box sx={{backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px"}}>
                        {mapResult()}
                    </Box>
                    <Box sx={{backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px"}}>
                        <Typography sx={{fontWeight: "bold", fontSize: "1.8rem"}}>{titleOfReport}</Typography>
                    </Box>
                    <Box sx={{backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px"}}>
                        <Typography sx={{display: "flex", fontWeight: "bold", fontSize: "1.2rem"}}>
                            <LightbulbIcon sx={{alignSelf: "center", marginRight: "10px"}}/>
                            Analizowane źródło
                        </Typography>
                        <Typography sx={{display: "flex", alignItems: "flex-start", alignSelf: "center", marginTop: "5px", fontWeight: "light"}}>
                            <CircleIcon sx={sourceDot}/>
                            <Link target="_blank" href={analyzedSource} sx={sourceLink}>
                                {getDomain(analyzedSource)}
                            </Link>
                        </Typography>
                    </Box>
                    <Box sx={{backgroundColor: theme.palette.background.paper, borderRadius: "15px", padding: "10px"}}>
                        <Typography sx={{display: "flex", fontWeight: "bold", fontSize: "1.2rem"}}>
                            <AutoAwesomeIcon sx={{alignSelf: "center", marginRight: "10px"}}/>
                            Znalezione źródła
                        </Typography>
                        {
                            foundSource.map((source, index) => (
                                <Typography key={index} sx={{display: "flex", alignItems: "flex-start", marginTop: "15px", fontWeight: "light"}}>
                                    <CircleIcon sx={sourceDot}/>
                                    <Link target="_blank" href={source} sx={sourceLink}>
                                        {getDomain(source)}
                                    </Link>
                                </Typography>
                            ))
                        }
                    </Box>
                </Box>
                <Box sx={{display: "flex", flexDirection: "column", maxHeight: isMobile ? "calc(50vh - 100px)" : "calc(100vh - 210px)", flex: 2}}>
                    <Typography sx={{fontWeight: "bold", fontSize: "2rem"}}>Raport</Typography>
                    <Box sx={{display: "flex", height: "calc(100vh - 210px)", overflowY: "auto", textAlign: "justify", textJustify: "inter-word"}}>
                        Zawartość raportu. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. . Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum."Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum. "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
                    </Box>
                </Box>
            </Box>
        )
    }

    return (
    <>
        <NavBar menuActive={menuActive} setMenuActive={setMenuActive} />
        <Box sx={{display: "flex", width: "100vw"}}>

            {isMobile ?
                <>
                    {menuActive && sideMenu()}
                </>
            :
                <>
                    {menuActive && sideMenu()}
                </>
            }

            <Box sx={{display: "flex", flexDirection: "column", width: isMobile ? "100%" : "80%", height: "calc(100vh - 100px)", padding: "10px", margin: "auto"}}>
                {isContentLoaded ?
                    content()
                :
                    isContentLoading ?
                        <Box sx={{display: "flex", flexDirection: "column", width: "250px", height: "250px",
                            margin: "auto", p: "15px", alignContent: "center",
                            backgroundColor: theme.palette.background.paper, borderRadius: "15px", boxShadow: 10}}>
                            <CircularProgress size="4.5rem" sx={{margin: "auto"}}/>
                            <Typography sx={{textAlign: "center", fontSize:"1.4rem", fontWeight:"light", color: theme.palette.text.secondary}}>
                                {loadingStatus}
                            </Typography>
                        </Box>
                        :
                        <Box sx={{margin: "auto", display: "flex", flexDirection: "column"}}>
                            <Typography sx={{display: "flex", fontSize:"2rem", fontWeight:"bold", color: theme.palette.background.paper}}>
                                <SearchOutlinedIcon sx={{ alignSelf: "center", marginRight: "15px", fontSize: "3rem"}} />
                                Podaj źródło, żeby sprawdzić jego wiarygodność
                            </Typography>
                        </Box>
                }
                <Box sx={{display: "flex", height: "70px", width: "100%", backgroundColor: theme.palette.background.paper, borderRadius: "15px", marginTop: "auto", boxShadow: "0 0 10px #181a20"}}>
                    <Input value={value} onChange={(e) => setValue(e.target.value)}
                           placeholder="Podaj źródło" disableUnderline={true}
                           sx={{fontWeight: "light", fontSize: "1.2rem", padding: "10px", width: "100%"}} />
                    <Button aria-label={"search-button"} onClick={handleSearch} sx={{m: "5px", borderRadius: "10px"}}>
                        <SearchOutlinedIcon sx={{alignSelf: "center", m: "5px", fontSize: "2rem"}} />
                    </Button>
                </Box>
            </Box>

        </Box>
    </>
    )
}

export default Raports;
