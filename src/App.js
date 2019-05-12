import React, { Component } from "react";
import { MuiThemeProvider, createMuiTheme } from "@material-ui/core/styles";
import "./App.css";
import Routes from "./routes";
import { blue, indigo } from "@material-ui/core/colors";
import StoreProvider from "./components/StoreProvider";

const theme = createMuiTheme({
  palette: {
    secondary: {
      main: blue[900]
    },
    primary: {
      main: indigo[700]
    }
  },
  typography: {
    // Use the system font instead of the default Roboto font.
    fontFamily: ['"Lato"', "sans-serif"].join(",")
  }
});

const App = ({ store }) => {
  return (
    <div>
      <StoreProvider store={store}>
        <MuiThemeProvider theme={theme}>
          <Routes />
        </MuiThemeProvider>
      </StoreProvider>
    </div>
  );
};

export default App;
