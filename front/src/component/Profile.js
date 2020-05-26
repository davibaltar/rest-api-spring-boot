import React from 'react';
import Typography from '@material-ui/core/Typography';
import NavBar from "./Navbar";
import AuthService from '../service/AuthService';


class Profile extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            message: '',
        }

    }

    componentDidMount() {
        if (!AuthService.getUserInfo())
            this.props.history.push('/login');
    }

    validateUser = () => {
        try {
            AuthService.validade().then(res => {
                if (res.status === 200) {
                    this.props.history.push('/perfil');
                } else {
                    this.props.history.push('/login');
                }
            });
        } catch (err) {
            this.props.history.push('/login');
        }
    };

    onChange = (e) =>
        this.setState({ [e.target.name]: e.target.value });

    closeMsg = () => {
        this.setState({ message: '' })
    }

    render() {
        return (
            <React.Fragment>
                <NavBar />
                <Typography variant="h4" style={styles.userTitle}>Profile (PRIVATE)</Typography>
            </React.Fragment>
        )
    }
}

const styles = {
    center: {
        display: 'flex',
        justifyContent: 'center',
        marginTop: 15
    },
    notification: {
        display: 'flex',
        justifyContent: 'center',
        color: '#dc3545'
    },
    user: {
        display: 'flex',
        justifyContent: 'center',
        color: '#b36161',
        fontSize: 14,
        paddingTop: 5
    },
    userTitle: {
        display: 'flex',
        justifyContent: 'center',
        color: '#b36161',
        fontSize: 14,
        marginTop: 100
    }
}


export default Profile;