import React from 'react';
import FixedBar from './FixedBar';
import { Grid } from '@material-ui/core';


class NotFound extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            message: '',
        }

    }

    componentDidMount() {
        localStorage.clear();
    }

    render() {
        window.scrollTo(0, 0)
        return (
            <React.Fragment>
                <FixedBar />
                <Grid
                    container
                    justify="center"
                    spacing={4}
                >
                    <Grid
                        item
                        lg={6}
                        xs={12}
                    >
                        <div className={styles.content}>
                            <div id="notfound">
                                <div class="notfound">
                                    <div class="notfound-404">
                                        <h1>:(</h1>
                                    </div>
                                    <h2>404 - Page not found</h2>
                                    <p>The page you are looking for might have been removed had its name changed or is temporarily unavailable.</p>
                                    <br />
                                    <a href="/">GO TO HOMEPAGE</a>
                                </div>
                            </div>
                        </div>
                    </Grid>
                </Grid>
            </React.Fragment>
        )
    }
}

const styles = {
    content: {
        paddingTop: 150,
        textAlign: 'center'
    }
}


export default NotFound;