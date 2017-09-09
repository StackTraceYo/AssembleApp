FROM trion/ng-cli-e2e

RUN mkdir -p /usr/src/app

WORKDIR /usr/src/app

COPY . /usr/src/app

RUN yarn install

EXPOSE 4200

CMD ["npm", "start"]
