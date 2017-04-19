from flask import Flask, render_template,url_for,request
from difflib import SequenceMatcher
import os

app = Flask(__name__)


@app.route('/')
def init():
    return render_template('index.html')

@app.route('/plagcheck/', methods=['POST'])
def plagcheck():
    APP_ROOT = os.path.dirname(os.path.abspath(__file__))
    APP_STATIC = os.path.join(APP_ROOT, 'static')
    inputtext=request.form['inputtext']
    similarity_ratio =[]
    for itr in range(1,5):
        with open(os.path.join(APP_STATIC, str(itr))) as dfile:
            dfile_data = dfile.read()
        similarity_ratio.append(SequenceMatcher(None,inputtext,dfile_data).ratio())
    return render_template('result.html', inputtext=max(similarity_ratio)*1000)


if __name__ == '__main__':
    app.run(debug=True)
