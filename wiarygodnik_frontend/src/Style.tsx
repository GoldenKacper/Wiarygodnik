import theme from "./theme.ts";

export const userPageButton = {
    width: "100%",
    display: "flex",
    flexDirection: "row",
    marginY: "5px",
    "&:hover": {
        backgroundColor: theme.palette.background.default
    },
};

export const userPageDivider = {
    backgroundColor: theme.palette.background.default, width: "100%"
};

export const userPageButtonIcon = {
    marginLeft: 0, marginRight: "auto"
};

export const userPageButtonText = {
    fontWeight: "regular", marginRight: "auto"
};

export const sourceLink = {
    textDecoration: "none",
    color: theme.palette.text.secondary,
    wordBreak: "break-all",
    overflowWrap: "anywhere",
    display: "block",
    fontSize: "1.1rem",
    "&:hover": {
        color: theme.palette.text.primary
    },
};

export const sourceDot = {
    color: theme.palette.text.secondary, fontSize: "0.7rem", marginRight: "10px", marginTop: "6px", alignSelf: "center"
};

export const sideMenuButton = {
    fontWeight: "light",
    marginBottom: "10px",
    borderRadius: "10px",
    justifyContent: "flex-start",
    textAlign: "left",
    "&:hover": {
        backgroundColor: "#131519"
    },
};
