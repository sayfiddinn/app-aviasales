package uz.pdp.appaviauz.util;

public interface HTML {
    String error403= """
            <!DOCTYPE html>
            <html lang="en">
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>403 Unauthorized</title>
                <style>
                    body {
                        font-family: Arial, sans-serif;
                        text-align: center;
                        background-color: #f2f2f2;
                        padding: 100px;
                    }
                    h1 {
                        color: #333;
                    }
                    p {
                        color: #777;
                    }
                </style>
            </head>
            <body>
            <h1>403 Unauthorized</h1>
            <p>Sizga bu sahifaga kirish ruxsat etilmagan.</p>
            </body>
            </html>
            """;
    String error404= """
            <!DOCTYPE html>
            <html lang="en">
            <head>
              <meta charset="UTF-8">
              <title>Not Found (#404)</title>
              <style>* {
                -webkit-box-sizing: border-box;
                box-sizing: border-box;
              }
                        
              body {
                padding: 0;
                margin: 0;
              }
                        
              #notfound {
                position: relative;
                height: 100vh;
              }
                        
              #notfound .notfound {
                position: absolute;
                left: 50%;
                top: 50%;
                -webkit-transform: translate(-50%, -50%);
                -ms-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
              }
                        
              .notfound {
                max-width: 710px;
                width: 100%;
                padding-left: 190px;
                line-height: 1.4;
              }
                        
              .notfound .notfound-404 {
                position: absolute;
                left: 0;
                top: 0;
                width: 150px;
                height: 150px;
              }
                        
              .notfound .notfound-404 h1 {
                font-family: 'Passion One', cursive;
                color: #d4135b;
                font-size: 150px;
                letter-spacing: 16px;
                margin: 0;
                font-weight: 900;
                position: absolute;
                left: 50%;
                top: 50%;
                -webkit-transform: translate(-50%, -50%);
                -ms-transform: translate(-50%, -50%);
                transform: translate(-50%, -50%);
              }
                        
              .notfound h2 {
                font-family: 'Raleway', sans-serif;
                color: #292929;
                font-size: 28px;
                font-weight: 700;
                text-transform: uppercase;
                letter-spacing: 3px;
                margin-top: 0;
              }
                        
              .notfound p {
                font-family: 'Raleway', sans-serif;
                font-size: 14px;
                font-weight: 400;
                margin-top: 0;
                margin-bottom: 15px;
                color: #333;
              }
                        
              .notfound a {
                font-family: 'Raleway', sans-serif;
                font-size: 14px;
                text-decoration: none;
                text-transform: uppercase;
                background: #fff;
                display: inline-block;
                padding: 15px 30px;
                border-radius: 40px;
                color: #292929;
                font-weight: 700;
                -webkit-box-shadow: 0 4px 15px -5px rgba(0, 0, 0, 0.3);
                box-shadow: 0 4px 15px -5px rgba(0, 0, 0, 0.3);
                -webkit-transition: 0.2s all;
                transition: 0.2s all;
              }
                        
              .notfound a:hover {
                color: #fff;
                background-color: #d4135b;
              }
                        
              @media only screen and (max-width: 480px) {
                .notfound {
                  text-align: center;
                }
                .notfound .notfound-404 {
                  position: relative;
                  width: 100%;
                  margin-bottom: 15px;
                }
                .notfound {
                  padding-left: 15px;
                  padding-right: 15px;
                }
              }</style></head>
            <body>
            <div id="notfound">
              <div class="notfound">
                <div class="notfound-404">
                  <h1>:(</h1>
                </div>
                <h2>Not Found (#404)</h2>
                <p>Sahifa topilmadi.</p>
                <a href="/" >Ortga</a>
              </div>
            </div>
            </body>
            </html>
            """;
}
