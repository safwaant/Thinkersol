import axios from 'axios';

// TODO still not sure if we can just use a single instance of admin axios or if we also need this manageallaxios?
// TODO is the baseURL for this API different? or is this the same API as the one used for Admin analytics?

// Domain + base for the admin analytics API.
const ADMIN_ANALYTICS_BASE_URL = 'https://mpaf3u2bse.us-west-2.awsapprunner.com/'; // TODO get the correct baseURL (this is currently hardcoded)
const AdminAxios = axios.create({ baseURL: ADMIN_ANALYTICS_BASE_URL,
                                    timeout: 2000 });
//
export default AdminAxios;