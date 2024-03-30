import HtmlWebpackPlugin from "html-webpack-plugin";
import path from "path";

module.exports = {
    devServer: {
        port: 9000,
        static: {
            directory: path.join(__dirname, 'public')
        }
    },
    module: {
        rules: [
            {
                test: /\.js$/,
                exclude: /node_modules/,
                use: {
                    loader: 'babel-loader'
                }
            }
        ]
    },
    plugins: [
        new HtmlWebpackPlugin({
            template: './public/index.html',
            filename: './index.html'
        })
    ]
};