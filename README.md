# rest-messenger
In addition could be done:
- added swagger annotations to document rest endpoints
- some cosmetic refactorings in MessageTypeRouter (in case of many message types switch clause could be replaced by strategy with factories) 
- sms feature commit had fix for one field related to email recipient - to make it in very clean way, shelf should be used and change extracted as separate commit. In general every commit should contain changes specifically related to feature.
