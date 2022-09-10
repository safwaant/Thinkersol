/**
 * This project is licensed under the GNU GPL v3 license
 * @author Shakeel Khan
 * @version 1.0
 * @since 7/29/2022
 * 
 * Abstract:
 *  - Contains an Axios object for the Admin Analytics API endpoint.
 *  - Simply import the default Axios object which you can then call get, put, post, etc. to make
 *    requests.
 */

import axios from 'axios';

// Domain + base for the admin analytics API.
const ADMIN_ANALYTICS_BASE_URL = 'https://mpaf3u2bse.us-west-2.awsapprunner.com/admin/analytics';
const AdminAxios = axios.create({ baseURL: ADMIN_ANALYTICS_BASE_URL,
                                    timeout: 2000 });

export default AdminAxios;