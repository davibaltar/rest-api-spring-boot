import React from 'react'
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';


const FixedBar = () => {
    return (
        <AppBar position="static">
            <Toolbar>
                <Typography variant="h6">
                    Locadora
                </Typography>
            </Toolbar>
        </AppBar>
    )
}

export default FixedBar;
