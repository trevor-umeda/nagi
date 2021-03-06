package nagi

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
class AnswerController {
    Random random = new Random()

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond Answer.list(params), model:[answerInstanceCount: Answer.count()]
    }

    def show(Answer answerInstance) {
        respond answerInstance
    }

    def create() {
        respond new Answer(params)
    }

    @Transactional
    def save(Answer answerInstance) {
        if (answerInstance == null) {
            notFound()
            return
        }

        if (answerInstance.hasErrors()) {
            respond answerInstance.errors, view:'create'
            return
        }

        answerInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'answerInstance.label', default: 'Answer'), answerInstance.id])
                redirect answerInstance
            }
            '*' { respond answerInstance, [status: CREATED] }
        }
    }

    def edit(Answer answerInstance) {
        respond answerInstance
    }

    @Transactional
    def update(Answer answerInstance) {
        if (answerInstance == null) {
            notFound()
            return
        }

        if (answerInstance.hasErrors()) {
            respond answerInstance.errors, view:'edit'
            return
        }

        answerInstance.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'Answer.label', default: 'Answer'), answerInstance.id])
                redirect answerInstance
            }
            '*'{ respond answerInstance, [status: OK] }
        }
    }

    @Transactional
    def delete(Answer answerInstance) {

        if (answerInstance == null) {
            notFound()
            return
        }

        answerInstance.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'Answer.label', default: 'Answer'), answerInstance.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'answerInstance.label', default: 'Answer'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }

    @Transactional
    def answerQuestion(){
        String question
        if(params.question){
            question = params.question
        }
        else{
            question = request.JSON?.question
        }
        if(question.contains("what would shungo say")) {
            def randomInt = random.nextInt(Phrase.count)
            def phrase = Phrase.all[randomInt]
            render phrase.body
        }
        else {
            def answers = Answer.findAllByQuestion(question)
            if(answers){
                def randomInt = random.nextInt(answers.size())
                render answers[randomInt].body
            }
            else{
                def answer = new Answer(question:question,body:"I'll have to think about that")
                answer.save(flush:true)
                render "I'll have to think about that"

            }

        }
    }
}
