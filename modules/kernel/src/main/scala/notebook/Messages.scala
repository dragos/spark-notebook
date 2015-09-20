package notebook.client

import notebook.util.Match

sealed trait CalcRequest

case class ExecuteRequest(counter: Int, code: String) extends CalcRequest

case class CompletionRequest(line: String, cursorPosition: Int) extends CalcRequest

case class InspectRequest(objName: String, position: Int) extends CalcRequest

case object InterruptRequest extends CalcRequest

sealed trait CalcResponse

case class StreamResponse(data: String, name: String) extends CalcResponse

case class ExecuteResponse(outputType:String, content: String, time:String) extends CalcResponse

case class ErrorResponse(message: String, incomplete: Boolean) extends CalcResponse

// CY: With high probability, the matchedText field is the segment of the input line that could
// be sensibly replaced with (any of) the candidate.
// i.e.
//
// input: "abc".inst
// ^
// the completions would come back as List("instanceOf") and matchedText => "inst"
//
// ...maybe...
case class CompletionResponse(cursorPosition: Int, candidates: Seq[Match], matchedText: String)

/**
    # 'ok' if the request succeeded or 'error', with error information as in all other replies.
    'status' : 'ok',
    'found'  : Boolean,

    # data can be empty if nothing is found
    # otherwise a dictionary from mime type to data in that format
    # for instance, "text/plain": "This is a tooltip"
    'data' : dict,
    'metadata' : dict,
*/
case class InspectResponse(found: Boolean, data: Map[String, String], metadata: Map[String, String])
