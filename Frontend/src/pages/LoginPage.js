// React component imports.
import { React, useState } from 'react';

import Card from '@mui/material/Card';
import CardContent from '@mui/material/CardContent';
import Grid from "@mui/material/Grid";
import Box from '@mui/material/Box';
import TextField from '@mui/material/TextField';
import Button from '@mui/material/Button';
import Stack from '@mui/material/Stack';

// Stylesheet imports.
import './LoginPage.css';


export default function LoginPage(){
    return(
        <div>
            <Grid
                container
                direction="row"
                justifyContent="center"
                alignItems="center"
            >
                <Card sx={{ maxWidth: '50em' }}>
                    <CardContent>
                        <Stack spacing={2}>
                        <h1>Login to your account</h1> 
                        <TextField
                        required
                        id="outlined-required"
                        label="Email"
                        />

                        <TextField
                        required
                        id="outlined-required"
                        label="Password"
                        />
                        <Button variant="contained">Login</Button>
                        </Stack>

                    </CardContent>
                </Card>
            </Grid>
        </div>
    );
}